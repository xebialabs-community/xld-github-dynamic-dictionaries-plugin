/**
 * Copyright 2021 XEBIALABS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package ext.deployit.community.ci.dictionary;

import java.util.Map;

import org.junit.Test;
import com.google.common.collect.Maps;

import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ScriptRunnerTest extends TestCase {

    private static Logger logger = LoggerFactory.getLogger(ScriptRunnerTest.class);

    public Map<String, String> loadData(String scriptFile) {
        final Map<String, Object> context = Maps.newHashMap();

        //context.put(ScriptRunner.KEY_ENTRIES, new HashMap());
        Map<String, Object> stringObjectMap = ScriptRunner.executeScript(context, scriptFile);
        if (logger.isDebugEnabled()) {
            logger.debug("stringObjectMap is " + stringObjectMap);
        }
        Map<String, String> data = (Map<String, String>) stringObjectMap.get(ScriptRunner.KEY_ENTRIES);
        if (logger.isDebugEnabled()) {
            logger.debug("data is " + data);
        }
        return data;
    }

    @Test
    public void test1() throws Exception {
        final Map<String, Object> map = Maps.newHashMap();
        map.put("url", "https://dict.github.com/bmoussaud/xld-petclinic-docker/blob/master/dar/config/log4j.properties");

        final Map<String, Object> stringObjectMap = ScriptRunner.executeScript(map, "test/load_data_test.py");
        final Map<String, String> entries = (Map<String, String>) stringObjectMap.get("dynamic_entries");

        assertTrue(entries.size() > 0);
        assertTrue(entries.get("benoit").equals("moussaud"));

    }

    @Test
    public void test_load_data() throws Exception {
        final Map<String, Object> map = Maps.newHashMap();
        map.put("url", "https://dict.github.com/bmoussaud/xld-petclinic-docker/blob/master/dar/config/log4j.properties");
        final Map<String, String> entries = loadData("test/load_data_test.py");
        assertTrue(entries.size() > 0);
        assertTrue(entries.get("benoit").equals("moussaud"));
    }

}
