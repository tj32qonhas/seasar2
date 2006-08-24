/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.framework.container.hotdeploy.creator;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.hotdeploy.OndemandCreator;
import org.seasar.framework.convention.NamingConvention;

/**
 * @author shot
 */
public class ConverterOndemandCreatorTest extends OndemandCreatorTestCase {

    protected OndemandCreator newOndemandCreator(NamingConvention convention) {
        return new ConverterOndemandCreator(convention);
    }

    public void testAll() throws Exception {
        String name = "fakeConverter";
        ComponentDef cd = getComponentDef(name);
        assertNotNull(cd);
        assertEquals(name, cd.getComponentName());
    }

    public void testGetComponentClassName() throws Exception {
        String name = "fakeConverter";
        String className = creator.getComponentClassName(ondemand,
                rootPackageName, name);
        assertNotNull("1", className);
        assertEquals("2", rootPackageName + ".converter.FakeConverter",
                className);
    }

}