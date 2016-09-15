/**
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS
 * FOR A PARTICULAR PURPOSE. THIS CODE AND INFORMATION ARE NOT SUPPORTED BY XEBIALABS.
 */
package ext.deployit.community.ci.dictionary;


import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.cache.*;

import com.xebialabs.deployit.plugin.api.udm.Dictionary;
import com.xebialabs.deployit.plugin.api.udm.Metadata;
import com.xebialabs.deployit.plugin.api.udm.Property;

@Metadata(
        root = Metadata.ConfigurationItemRoot.ENVIRONMENTS,
        description = "A Dictionary that resolves the value dynamically",
        virtual = true
)
public abstract class BaseDynamicDictionary extends Dictionary {

    @Property(description = "Reload the entries during the planning phase", defaultValue = "True", category = "Advanced")
    private boolean dynamicLoad;

    @Property(description = "Use a cache (10 seconds)...", defaultValue = "True", category = "Advanced", hidden = true)
    private boolean useCache;

    @Override
    public Map<String, String> getEntries() {
        if (dynamicLoad) {
            logger.debug("dynamicLoad True");
            Map data;
            if (useCache) {
                data = cache.getUnchecked(this);
            } else {
                data = loadData();
            }
            logger.debug("docker-machines " + data);
            return data;
        } else {
            return super.getEntries();
        }
    }


    private static LoadingCache<BaseDynamicDictionary, Map<String, String>> cache =
            CacheBuilder.newBuilder()
                    .expireAfterAccess(10, TimeUnit.SECONDS)
                    .removalListener(new RemovalListener<Object, Object>() {
                        @Override
                        public void onRemoval(final RemovalNotification<Object, Object> removalNotification) {
                            logger.info("Remove " + removalNotification.getKey() + " from docker-machine cache.....");
                        }
                    })
                    .build(new CacheLoader<BaseDynamicDictionary, Map<String, String>>() {
                        @Override
                        public Map<String, String> load(final BaseDynamicDictionary s) throws Exception {
                            return s.loadData();
                        }
                    });

    public Map<String, String> loadData() {
        logger.error("Return empty data");
        return Collections.emptyMap();
    }

    private static Logger logger = LoggerFactory.getLogger(BaseDynamicDictionary.class);
}
