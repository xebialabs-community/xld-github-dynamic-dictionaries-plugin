/**
 * Copyright 2017 XebiaLabs
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
