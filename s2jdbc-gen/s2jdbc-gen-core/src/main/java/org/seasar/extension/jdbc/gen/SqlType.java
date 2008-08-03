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
package org.seasar.extension.jdbc.gen;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * SQL型です。
 * <p>
 * JDBCのSQL型、つまり{@link Types}の定数に対応します。
 * </p>
 * 
 * @author taedium
 */
public interface SqlType {

    /**
     * カラム定義を返します。
     * 
     * @param length
     *            長さ
     * @param precision
     *            精度
     * @param scale
     *            スケール
     * @param identity
     *            IDENTITYカラムの場合{@code true}
     * @return カラム定義
     */
    String getColumnDefinition(int length, int precision, int scale,
            boolean identity);

    String getValue(ResultSet resultSet, int index) throws SQLException;

    void bindValue(PreparedStatement ps, int index, String value)
            throws SQLException;
}
