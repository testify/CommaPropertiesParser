/*
 * Copyright 2015 Codice Foundation
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.codice.testify.propertiesParsers.Comma;

import org.codice.testify.objects.TestProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(JUnit4.class)
public class CommaPropertiesParserTest {

    //Set Objects
    private final CommaPropertiesParser commaPropertiesParser = new CommaPropertiesParser();
    private TestProperties testProperties = null;
    private final String currentDir = System.getProperty("user.dir");
    private final String configFile = currentDir + "/src/test/resources/test.properties";
    private final List<String> propertyList = Arrays.asList("TEST1","TEST2","TEST3");
    private final List<String> TEST1List = Arrays.asList("STUFF1");
    private final List<String> TEST2List = Arrays.asList("PART1","PART2","PART3","PART4","PART5","PART6");
    private final List<String> TEST3List = Arrays.asList("DATA1","DATA2");

    @Test
    public void testDataReturn() {
        testProperties = commaPropertiesParser.getTestProperties(configFile);
        assert( testProperties != null );
    }

    @Test
    public void testCorrectProperties() {
        testProperties = commaPropertiesParser.getTestProperties(configFile);
        List<String> allProperties = new ArrayList<>(testProperties.getPropertyNames());
        Collections.sort(allProperties);
        assert( allProperties.equals(propertyList) );
    }

    @Test
    public void testCorrectValues() {
        testProperties = commaPropertiesParser.getTestProperties(configFile);
        assert( testProperties.getValues("TEST1").equals(TEST1List) );
        List<String> TEST2Properties = testProperties.getValues("TEST2");
        Collections.sort(TEST2Properties);
        assert( TEST2Properties.equals(TEST2List) );
        List<String> TEST3Properties = testProperties.getValues("TEST3");
        Collections.sort(TEST3Properties);
        assert( TEST3Properties.equals(TEST3List) );
    }

}