/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.extension.jdbc.gen.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.seasar.extension.jdbc.EntityMeta;
import org.seasar.extension.jdbc.gen.TestModel;

import static org.junit.Assert.*;

/**
 * @author taedium
 * 
 */
public class TestModelFactoryImplTest {

    private TestModelFactoryImpl factory;

    /**
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        factory = new TestModelFactoryImpl("s2jdbc.dicon", "jdbcManager",
                "hoge", "Test");
    }

    /**
     * 
     * @throws Exception
     */
    @Test
    public void testGetEntityTestModel() throws Exception {
        EntityMeta entityMeta = new EntityMeta();
        entityMeta.setName("Foo");
        TestModel testModel = factory.getEntityTestModel(entityMeta);
        assertEquals("s2jdbc.dicon", testModel.getConfigPath());
        assertEquals("jdbcManager", testModel.getJdbcManagerName());
        assertEquals("hoge", testModel.getPackageName());
        assertEquals("FooTest", testModel.getShortClassName());
        assertEquals("Foo", testModel.getShortEntityClassName());
        assertEquals(2, testModel.getImportNameSet().size());
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_Boolean() {
        assertEquals("true", factory.getIdExpression(Boolean.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_Character() {
        assertEquals("'a'", factory.getIdExpression(Character.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_Byte() {
        assertEquals("(byte) 1", factory.getIdExpression(Byte.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_Short() {
        assertEquals("(short) 1", factory.getIdExpression(Short.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_Integer() {
        assertEquals("1", factory.getIdExpression(Integer.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_Long() {
        assertEquals("1L", factory.getIdExpression(Long.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_Float() {
        assertEquals("1f", factory.getIdExpression(Float.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_Double() {
        assertEquals("1d", factory.getIdExpression(Double.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_BigDecimal() {
        assertEquals("new BigDecimal(1)", factory
                .getIdExpression(BigDecimal.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_BigInteger() {
        assertEquals("new BigInteger(1)", factory
                .getIdExpression(BigInteger.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_String() {
        assertEquals("\"aaa\"", factory.getIdExpression(String.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_Date() {
        assertEquals("new Date()", factory.getIdExpression(Date.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_Calender() {
        assertEquals("Calendar.getInstance()", factory
                .getIdExpression(Calendar.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_sqlDate() {
        assertEquals("Date.valueOf(\"2008-01-01\")", factory
                .getIdExpression(java.sql.Date.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_Time() {
        assertEquals("Time.valueOf(\"12:00:00\")", factory
                .getIdExpression(Time.class));
    }

    /**
     * 
     */
    @Test
    public void testGetIdExpression_Timestamp() {
        assertEquals("Timestamp.valueOf(\"2008-01-01 12:00:00\")", factory
                .getIdExpression(Timestamp.class));
    }
}
