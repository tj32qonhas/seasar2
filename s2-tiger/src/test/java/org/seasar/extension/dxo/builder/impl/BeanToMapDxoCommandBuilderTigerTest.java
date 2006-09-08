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
package org.seasar.extension.dxo.builder.impl;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.extension.dxo.annotation.impl.AnnotationReaderFactoryImpl;

/**
 * @author koichik
 */
public class BeanToMapDxoCommandBuilderTigerTest extends TestCase {

    private BeanToMapDxoCommandBuilder builder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        builder = new BeanToMapDxoCommandBuilder();
        builder.setAnnotationReaderFactory(new AnnotationReaderFactoryImpl());
    }

    public void testApplicable() throws Exception {
        assertNotNull(builder.createDxoCommand(ToListDxo.class, ToListDxo.class
                .getMethod("convert", new Class[] { Object[].class })));
        assertNotNull(builder.createDxoCommand(ToListDxo.class, ToListDxo.class
                .getMethod("convert", new Class[] { List.class })));

        assertNull(builder.createDxoCommand(ToListDxo.class, ToListDxo.class
                .getMethod("convert", new Class[] { String[].class })));
    }

    public interface ToListDxo {
        String convert_CONVERSION_RULE = "";

        List<Map> convert(Object[] src);

        List<Map> convert(List<Object> src);

        List<Integer> convert(String[] src);
    }

}