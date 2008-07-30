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
import java.math.BigInteger;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.GenerationType;
import javax.persistence.TemporalType;

/**
 * Oracleの方言を扱うクラスです。
 * 
 * @author taedium
 */
public class OracleGenDialect extends StandardGenDialect {

    /** テーブルが見つからないことを示すエラーコード */
    protected static int TABLE_NOT_FOUND_ERROR_CODE = 942;

    /**
     * インスタンスを構築します。
     */
    public OracleGenDialect() {
        typeMap.put(Types.BINARY, OracleSqlType.BINARY);
        typeMap.put(Types.BOOLEAN, OracleSqlType.BOOLEAN);
        typeMap.put(Types.BIGINT, OracleSqlType.BIGINT);
        typeMap.put(Types.DECIMAL, OracleSqlType.DECIMAL);
        typeMap.put(Types.DOUBLE, OracleSqlType.DOUBLE);
        typeMap.put(Types.INTEGER, OracleSqlType.INTEGER);
        typeMap.put(Types.SMALLINT, OracleSqlType.SMALLINT);
        typeMap.put(Types.TIME, OracleSqlType.TIME);
        typeMap.put(Types.VARCHAR, OracleSqlType.VARCHAR);

        namedTypeMap.put("binary_double", OracleColumnType.BINARY_DOUBLE);
        namedTypeMap.put("binary_float", OracleColumnType.BINARY_FLOAT);
        namedTypeMap.put("blob", OracleColumnType.BLOB);
        namedTypeMap.put("clob", OracleColumnType.CLOB);
        namedTypeMap.put("long", OracleColumnType.LONG);
        namedTypeMap.put("long raw", OracleColumnType.LONG_RAW);
        namedTypeMap.put("nchar", OracleColumnType.NCHAR);
        namedTypeMap.put("nclob", OracleColumnType.NCLOB);
        namedTypeMap.put("number", OracleColumnType.NUMBER);
        namedTypeMap.put("nvarchar2", OracleColumnType.NVARCHAR2);
        namedTypeMap.put("raw", OracleColumnType.RAW);
        namedTypeMap.put("varchar2", OracleColumnType.VARCHAR2);

        sqlBlockStartWordsList.add(Arrays.asList("create", "or", "replace",
                "procedure"));
        sqlBlockStartWordsList.add(Arrays.asList("create", "or", "replace",
                "function"));
        sqlBlockStartWordsList.add(Arrays.asList("create", "procedure"));
        sqlBlockStartWordsList.add(Arrays.asList("create", "function"));
        sqlBlockStartWordsList.add(Arrays.asList("declare"));
        sqlBlockStartWordsList.add(Arrays.asList("begin"));
    }

    @Override
    public boolean isUserTable(String tableName) {
        return !tableName.contains("$");
    }

    @Override
    public GenerationType getDefaultGenerationType() {
        return GenerationType.SEQUENCE;
    }

    @Override
    public boolean supportsSequence() {
        return true;
    }

    @Override
    public String getSequenceDefinitionFragment(String dataType, int initValue,
            int allocationSize) {
        return "increment by " + allocationSize + " start with " + initValue;
    }

    @Override
    public String getSqlBlockDelimiter() {
        return "/";
    }

    @Override
    public boolean isTableNotFound(Throwable throwable) {
        Integer errorCode = getErrorCode(throwable);
        return errorCode != null
                && errorCode.intValue() == TABLE_NOT_FOUND_ERROR_CODE;
    }

    @Override
    public ColumnType getColumnType(String typeName) {
        if (org.seasar.framework.util.StringUtil.startsWithIgnoreCase(typeName,
                "timestamp")) {
            return OracleColumnType.TIMESTAMP;
        }
        return super.getColumnType(typeName);
    }

    /**
     * Oracle用の{@link SqlType}の実装クラスです。
     * 
     * @author taedium
     */
    public static class OracleSqlType extends StandardSqlType {

        private static SqlType BIGINT = new OracleSqlType("number($p,0)");

        private static SqlType BINARY = new OracleSqlType() {

            @Override
            public String getColumnDefinition(int length, int precision,
                    int scale, boolean identity) {
                columnDefinition = length > 2000 ? "blob" : "raw($l)";
                return super.getColumnDefinition(length, precision, scale,
                        identity);
            }
        };

        private static SqlType BOOLEAN = new OracleSqlType("number(1,0)");

        private static SqlType DECIMAL = new OracleSqlType("number($p,$s)");

        private static SqlType DOUBLE = new OracleSqlType("double precision");

        private static SqlType INTEGER = new OracleSqlType("number(10,0)");

        private static SqlType SMALLINT = new OracleSqlType("number(5,0)");

        private static SqlType TIME = new OracleSqlType("date");

        private static SqlType VARCHAR = new OracleSqlType() {

            @Override
            public String getColumnDefinition(int length, int precision,
                    int scale, boolean identity) {
                columnDefinition = length > 4000 ? "clob" : "varchar2($l)";
                return super.getColumnDefinition(length, precision, scale,
                        identity);
            }
        };

        /**
         * インスタンスを構築します。
         */
        protected OracleSqlType() {
        }

        protected OracleSqlType(String columnDefinition) {
            super(columnDefinition);
        }
    }

    public static class OracleColumnType extends StandardColumnType {

        private static OracleColumnType BINARY_DOUBLE = new OracleColumnType(
                "binary_double", Double.class);

        private static OracleColumnType BINARY_FLOAT = new OracleColumnType(
                "binary_float", Float.class);

        private static OracleColumnType BLOB = new OracleColumnType("blob",
                byte[].class, true);

        private static OracleColumnType CLOB = new OracleColumnType("clob",
                String.class, true);

        private static OracleColumnType LONG_RAW = new OracleColumnType(
                "long raw", byte[].class);

        private static OracleColumnType LONG = new OracleColumnType("long",
                String.class);

        private static OracleColumnType NCHAR = new OracleColumnType(
                "nchar($l)", String.class) {

            @Override
            public String getColumnDefinition(int length, int precision,
                    int scale) {
                return super.getColumnDefinition(length / 2, precision, scale);
            }
        };

        private static OracleColumnType NCLOB = new OracleColumnType("nclob",
                String.class, true);

        private static OracleColumnType NUMBER = new OracleColumnType(
                "number($p,$s)", BigDecimal.class) {

            @Override
            public Class<?> getAttributeClass(int length, int precision,
                    int scale) {
                if (scale != 0) {
                    return BigDecimal.class;
                }
                if (precision == 1) {
                    return Boolean.class;
                }
                if (precision <= 5) {
                    return Short.class;
                }
                if (precision <= 10) {
                    return Integer.class;
                }
                if (precision <= 19) {
                    return Long.class;
                }
                return BigInteger.class;
            }
        };

        private static OracleColumnType NVARCHAR2 = new OracleColumnType(
                "nvarchar2($l)", String.class) {

            @Override
            public String getColumnDefinition(int length, int precision,
                    int scale) {
                return super.getColumnDefinition(length / 2, precision, scale);
            }
        };

        private static OracleColumnType RAW = new OracleColumnType("raw($l)",
                byte[].class);

        private static OracleColumnType TIMESTAMP = new OracleColumnType(
                "timestamp($s)", Date.class, TemporalType.TIMESTAMP);

        private static OracleColumnType VARCHAR2 = new OracleColumnType(
                "varchar2($l)", byte[].class);

        public OracleColumnType(String columnDefinition, Class javaClass) {
            super(columnDefinition, javaClass);
        }

        public OracleColumnType(String columnDefinition, Class<?> javaClass,
                boolean lob) {
            super(columnDefinition, javaClass, lob, null);
        }

        public OracleColumnType(String columnDefinition, Class<?> javaClass,
                TemporalType temporalType) {
            super(columnDefinition, javaClass, false, temporalType);
        }

    }
}
