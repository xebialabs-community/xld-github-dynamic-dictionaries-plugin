/**
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS
 * FOR A PARTICULAR PURPOSE. THIS CODE AND INFORMATION ARE NOT SUPPORTED BY XEBIALABS.
 */
package ext.deployit.community.ci.dictionary;

import java.util.Map;
import com.google.common.collect.Maps;

import com.xebialabs.deployit.plugin.api.reflect.Descriptor;
import com.xebialabs.deployit.plugin.api.reflect.PropertyDescriptor;
import com.xebialabs.deployit.plugin.api.udm.Metadata;
import com.xebialabs.deployit.plugin.api.udm.Property;

@Metadata(
        root = Metadata.ConfigurationItemRoot.ENVIRONMENTS,
        description = "A Dictionary that resolves the value dynamically using a pythons script",
        virtual = true
)
public class PythonDynamicDictionary extends BaseDynamicDictionary {

    @Property(description = "python script use to load the data", required = true, category = "Advanced")
    private String scriptFile;

    public Map<String, String> loadData() {

        final Map<String, String> map = Maps.newHashMap();
        final Descriptor descriptor = this.getType().getDescriptor();
        for (PropertyDescriptor propertyDescriptor : descriptor.getPropertyDescriptors()) {
            String name = propertyDescriptor.getName();
            map.put(name, this.getProperty(name));
        }
        return (Map<String, String>) ScriptRunner.executeScript(map, scriptFile).get(ScriptRunner.KEY_ENTRIES);
    }
}
