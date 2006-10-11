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
package org.seasar.framework.jpa.autodetector;

import java.util.ArrayList;
import java.util.List;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.autodetector.ResourceAutoDetector;

/**
 * @author taedium
 * 
 */
public class MappingFileAutoDetectorTest extends S2TestCase {

    private ResourceAutoDetector detector;

    @Override
    protected void setUp() throws Exception {
        include("MappingFileAutoDetectorTest.dicon");
    }

    public void testDetect() throws Exception {
        List<String> paths = new ArrayList<String>();
        for (ResourceAutoDetector.Entry entry : detector.detect()) {
            paths.add(entry.getPath());
        }
        assertEquals(5, paths.size());
        assertTrue(paths.contains("META-INF/orm.xml"));
        assertTrue(paths.contains("META-INF/fooOrm.xml"));
        assertTrue(paths
                .contains("org/seasar/framework/jpa/entity/hogeOrm.xml"));
        assertTrue(paths
                .contains("org/seasar/framework/jpa/sub/entity/barOrm.xml"));
        assertTrue(paths.contains("org/junit/runner/RunWith.class"));
    }
}