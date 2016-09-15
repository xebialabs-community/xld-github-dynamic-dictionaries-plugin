/**
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS
 * FOR A PARTICULAR PURPOSE. THIS CODE AND INFORMATION ARE NOT SUPPORTED BY XEBIALABS.
 */
package ext.deployit.community.ci.dictionary;

import java.util.Map;
import com.google.common.collect.Maps;

import com.xebialabs.deployit.plugin.api.udm.Property;

public class PythonDynamicDictionary extends BaseDynamicDictionary {

    @Property(description = "python script use to load the data", required = true, category = "Advanced")
    private String scriptFile;

    public Map<String, String> loadData() {

        final Map<String, String> map = Maps.newHashMap();
        return (Map<String, String>) ScriptRunner.executeScript(map, scriptFile).get(ScriptRunner.KEY_ENTRIES);
    }
}
