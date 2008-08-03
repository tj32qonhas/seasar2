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
package org.seasar.extension.jdbc.gen.dialect;

import java.math.BigDecimal;
import java.sql.Types;

import javax.persistence.GenerationType;
import javax.persistence.TemporalType;

import org.seasar.extension.jdbc.gen.sqltype.BinaryType;
import org.seasar.extension.jdbc.gen.sqltype.BlobType;
import org.seasar.extension.jdbc.gen.sqltype.BooleanType;
import org.seasar.extension.jdbc.gen.sqltype.ClobType;
import org.seasar.extension.jdbc.gen.sqltype.DecimalType;
import org.seasar.extension.jdbc.gen.sqltype.FloatType;

/**
 * Derbyの方言を扱うクラスです。
 * 
 * @author taedium
 */
public class DerbyGenDialect extends StandardGenDialect {

    /** テーブルが見つからないことを示すSQLステート */
    protected static String TABLE_NOT_FOUND_SQL_STATE = "42X05";

    /**
     * インスタンスを構築します。
     */
    public DerbyGenDialect() {
        sqlTypeMap
                .put(Types.BINARY, new BinaryType("varchar($l) for bit data"));
        sqlTypeMap.put(Types.BLOB, new BlobType("blob($l)"));
        sqlTypeMap.put(Types.BOOLEAN, new BooleanType("smallint"));
        sqlTypeMap.put(Types.CLOB, new ClobType("clob($l)"));
        sqlTypeMap.put(Types.DECIMAL, new DecimalType("decimal($p,$s)"));
        sqlTypeMap.put(Types.FLOAT, new FloatType("real"));

        columnTypeMap.put("blob", DerbyColumnType.BLOB);
        columnTypeMap.put("char () for bit data", DerbyColumnType.CHAR_BIT);
        columnTypeMap.put("clob", DerbyColumnType.CLOB);
        columnTypeMap.put("decimal", DerbyColumnType.DECIMAL);
        columnTypeMap.put("long varchar bit data",
                DerbyColumnType.LONGVARCHAR_BIT);
        columnTypeMap.put("long varchar", DerbyColumnType.LONGVARCHAR);
        columnTypeMap.put("varchar () for bit data",
                DerbyColumnType.VARCHAR_BIT);
    }

    @Override
    public GenerationType getDefaultGenerationType() {
        return GenerationType.IDENTITY;
    }

    @Override
    public String getIdentityColumnDefinition() {
        return "not null generated always as identity";
    }

    @Override
    public boolean isTableNotFound(Throwable throwable) {
        String sqlState = getSQLState(throwable);
        return TABLE_NOT_FOUND_SQL_STATE.equals(sqlState);
    }

    /**
     * Derby用の{@link ColumnType}の実装です。
     * 
     * @author taedium
     */
    public static class DerbyColumnType extends StandardColumnType {

        private static DerbyColumnType BLOB = new DerbyColumnType("blob($l)",
                byte[].class);

        private static DerbyColumnType CHAR_BIT = new DerbyColumnType(
                "char($l) for bit data", byte[].class);

        private static DerbyColumnType CLOB = new DerbyColumnType("clob($l)",
                String.class);

        private static DerbyColumnType DECIMAL = new DerbyColumnType(
                "decimal($p,$s)", BigDecimal.class);

        private static DerbyColumnType LONGVARCHAR_BIT = new DerbyColumnType(
                "long varchar for bit data", byte[].class);

        private static DerbyColumnType LONGVARCHAR = new DerbyColumnType(
                "long varchar", String.class);

        private static DerbyColumnType VARCHAR_BIT = new DerbyColumnType(
                "varchar($l) for bit data", byte[].class);

        /**
         * インスタンスを構築します。
         * 
         * @param columnDefinition
         * @param attributeClass
         */
        public DerbyColumnType(String columnDefinition, Class<?> attributeClass) {
            super(columnDefinition, attributeClass);
        }

        /**
         * インスタンスを構築します。
         * 
         * @param columnDefinition
         *            カラム定義
         * @param attributeClass
         *            属性のクラス
         * @param lob
         *            LOBの場合{@code true}
         */
        public DerbyColumnType(String columnDefinition,
                Class<?> attributeClass, boolean lob) {
            super(columnDefinition, attributeClass, lob);
        }

        /**
         * インスタンスを構築します。
         * 
         * @param columnDefinition
         *            カラム定義
         * @param attributeClass
         *            属性のクラス
         * @param temporalType
         *            時制型
         */
        public DerbyColumnType(String columnDefinition,
                Class<?> attributeClass, TemporalType temporalType) {
            super(columnDefinition, attributeClass, temporalType);
        }

    }
}
