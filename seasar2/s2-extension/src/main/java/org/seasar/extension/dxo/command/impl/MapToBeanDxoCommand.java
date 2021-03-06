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
package org.seasar.extension.dxo.command.impl;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.seasar.extension.dxo.annotation.AnnotationReader;
import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.Converter;
import org.seasar.extension.dxo.converter.ConverterFactory;

/**
 * {@link Map}からBeanに変換するコマンドです。
 * 
 * @author koichik
 */
public class MapToBeanDxoCommand extends BeanToBeanDxoCommand {

    /**
     * インスタンスを構築します。
     * 
     * @param dxoClass
     *            Dxoのインターフェースまたはクラス
     * @param method
     *            Dxoのメソッド
     * @param converterFactory
     *            {@link Converter}のファクトリ
     * @param annotationReader
     *            {@link AnnotationReader}のファクトリ
     * @param destClass
     *            変換先のクラス
     */
    public MapToBeanDxoCommand(final Class dxoClass, final Method method,
            final ConverterFactory converterFactory,
            final AnnotationReader annotationReader, final Class destClass) {
        super(dxoClass, method, converterFactory, annotationReader, destClass);
    }

    protected ConversionContext createContext(final Object source) {
        final ConversionContext context = super.createContext(source);
        final Map map = (Map) source;
        for (final Iterator it = map.entrySet().iterator(); it.hasNext();) {
            final Map.Entry entry = (Map.Entry) it.next();
            final String name = (String) entry.getKey();
            if (!context.hasEvalueatedValue(name)) {
                context.addEvaluatedValue(name, entry.getValue());
            }
        }
        return context;
    }

}
