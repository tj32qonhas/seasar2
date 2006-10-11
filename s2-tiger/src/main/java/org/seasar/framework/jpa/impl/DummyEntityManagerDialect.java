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
package org.seasar.framework.jpa.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.annotation.tiger.DestroyMethod;
import org.seasar.framework.container.annotation.tiger.InitMethod;
import org.seasar.framework.exception.SQLRuntimeException;
import org.seasar.framework.jpa.Dialect;
import org.seasar.framework.jpa.DialectManager;

/**
 * @author koichik
 * 
 */
public class DummyEntityManagerDialect implements Dialect {

    @Binding(bindingType = BindingType.MUST)
    protected DialectManager dialectManager;

    @InitMethod
    public void initialize() {
        dialectManager.addDialect(DummyEntityManager.class, this);
    }

    @DestroyMethod
    public void destroy() {
        dialectManager.removeDialect(DummyEntityManager.class);
    }

    public Connection getConnection(final EntityManager em) {
        try {
            return DummyEntityManager.class.cast(em).getDataSource()
                    .getConnection();
        } catch (final SQLException e) {
            throw new SQLRuntimeException(e);
        }
    }

}