/*
 * Copyright 2004-2013 the Seasar Foundation and the Others.
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
package org.seasar.extension.jdbc.dialect;

import org.seasar.extension.jdbc.dialect.Db2390Dialect;

import junit.framework.TestCase;

/**
 * @author higa
 * 
 */
public class Db2390DialectTest extends TestCase {

	private Db2390Dialect dialect = new Db2390Dialect();

	/**
	 * @throws Exception
	 */
	public void testConvertLimitSql_limitOnly() throws Exception {
		String sql = "select * from emp order by id";
		String expected = sql + " fetch first 5 rows only";
		assertEquals(expected, dialect.convertLimitSql(sql, 0, 5));

	}

	/**
	 * @throws Exception
	 */
	public void testConvertLimitSql_offsetLimit() throws Exception {
		String sql = "select e.* from emp e order by id";
		String expected = sql + " fetch first 15 rows only";
		assertEquals(expected, dialect.convertLimitSql(sql, 5, 10));

	}
}
