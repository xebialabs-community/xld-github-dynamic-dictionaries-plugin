/**
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS
 * FOR A PARTICULAR PURPOSE. THIS CODE AND INFORMATION ARE NOT SUPPORTED BY XEBIALABS.
 */
package ext.deployit.community.ci.dictionary;

import java.util.Map;
import org.junit.Test;
import com.google.common.collect.Maps;

import junit.framework.TestCase;


public class ScriptRunnerTest extends TestCase {

    @Test
    public void test1() throws Exception {
        final Map<String, Object> map = Maps.newHashMap();
        map.put("url", "https://dict.github.com/bmoussaud/xld-petclinic-docker/blob/master/dar/config/log4j.properties");

        final Map<String, Object> stringObjectMap = ScriptRunner.executeScript(map, "test/load_data_test.py");
        final Map<String, String> entries = (Map<String, String>) stringObjectMap.get("entries");

        assertTrue(entries.size() > 0);
        assertTrue(entries.get("benoit").equals("moussaud"));

    }

}