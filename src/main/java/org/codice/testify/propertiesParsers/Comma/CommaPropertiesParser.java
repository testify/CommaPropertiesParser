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

import org.codice.testify.objects.TestifyLogger;
import org.codice.testify.objects.TestProperties;
import org.codice.testify.propertiesParsers.PropertiesParser;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * The CommaPropertiesParser class is a Testify PropertiesParser service that splits values by comma
 */
public class CommaPropertiesParser implements BundleActivator, PropertiesParser {

    @Override
    public TestProperties getTestProperties(String s) {

        TestifyLogger.debug("Running CommaPropertiesParser", this.getClass().getSimpleName());
        TestProperties testProps = null;
        File file = new File(s);
        if (file.exists()) {
            try {

                //Store property and value pairs in props object
                testProps = new TestProperties();
                Properties props = new Properties();
                props.load(new FileReader(file));
                Set<String> names = props.stringPropertyNames();

                //Go through each property and split values by comma
                for (String name : names) {
                    String prop = props.getProperty(name);
                    String[] propArray = prop.split(",");
                    List<String> propList = new ArrayList<>();

                    //Store comma separated values in an array
                    for (String singleProp : propArray) {
                        propList.add(singleProp.trim());
                    }

                    //Add value array to test properties under the test name
                    testProps.addProperties(name, propList);
                }
            }catch (IOException e) {
                TestifyLogger.error(e.getMessage(), this.getClass().getSimpleName());
            }
        } else {
            TestifyLogger.debug("Properties file: " + s + " does not exist", this.getClass().getSimpleName());
        }
        return testProps;
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {

        //Register the Contains service
        bundleContext.registerService(PropertiesParser.class.getName(), new CommaPropertiesParser(), null);

    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}