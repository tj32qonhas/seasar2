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
package org.seasar.framework.unit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Parameterized.Parameters;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;
import org.seasar.framework.env.Env;
import org.seasar.framework.util.ResourceUtil;

/**
 * S2JUnit4を実行するための{@link org.junit.runner.Runner}です。
 * <p>
 * {@link org.junit.runner.RunWith}に指定して次のように利用します。
 * 
 * <pre>
 * &#064;RunWith(Seasar2.class)
 * public class HogeTest {
 *   ...
 * }
 * </pre>
 * 
 * </p>
 * 
 * @author taedium
 * 
 */
public class Seasar2 extends Runner implements Filterable, Sortable {

    /** S2JUnit4の振る舞いを設定するためのコンフィグレーションファイルのキー */
    public static final String S2JUNIT4_CONFIG_KEY = "org.seasar.framework.unit.s2junit4.config";

    /** S2JUnit4の振る舞いを設定するためのコンフィグレーションファイルのパス */
    public static final String S2JUNIT4_CONFIG_PATH = "s2junit4config.dicon";

    /** このランナーで使用する環境名設定ファイルのパス */
    public static final String ENV_PATH = "env_ut.txt";

    /** 環境名設定ファイルのパスにファイルが存在しない場合の環境名 */
    public static final String ENV_VALUE = "ut";

    /** コンフィグレーションファイルから構築されたコンフィグレーションS2コンテナ */
    protected static S2Container configurationContainer;

    /** {@link Seasar2}の振る舞いを提供するプロバイダ */
    protected static Provider provider;

    static {
        configure();
    }

    private final Runner delegate;

    /**
     * インスタンスを構築します。
     * 
     * @param clazz
     *            テストクラス
     * @throws Exception
     *             何らかの例外が発生した場合
     */
    public Seasar2(final Class<?> clazz) throws Exception {
        delegate = createTestClassRunner(clazz);
    }

    /**
     * テストクラスランナーを作成します。
     * 
     * @param clazz
     *            テストクラス
     * @return テストクラスランナー
     * @throws Exception
     *             何らかの例外が発生した場合
     */
    protected Runner createTestClassRunner(Class<?> clazz) throws Exception {
        return getProvider().createTestClassRunner(clazz);
    }

    /**
     * {@link Seasar2}の振る舞いを提供するプロバイダを返します。
     * 
     * @return 振る舞いを提供するプロバイダ
     */
    protected static Provider getProvider() {
        return provider;
    }

    /**
     * {@link Seasar2}の振る舞いを提供するプロバイダを設定します。
     * 
     * @param p
     *            振る舞いを提供するプロバイダ
     */
    protected static void setProvider(final Provider p) {
        provider = p;
    }

    /**
     * このクラスを設定します。
     */
    public static void configure() {
        final String configFile = System.getProperty(S2JUNIT4_CONFIG_KEY,
                S2JUNIT4_CONFIG_PATH);
        configure(configFile);
    }

    /**
     * このクラスを設定します。
     * 
     * @param configFile
     *            設定ファイルのパス
     */
    public static void configure(final String configFile) {
        Env.setFilePath(ENV_PATH);
        Env.setValueIfAbsent(ENV_VALUE);

        if (provider == null) {
            provider = new DefaultProvider();
        }

        if (ResourceUtil.isExist(configFile)) {
            configurationContainer = S2ContainerFactory.create(configFile);
            Configurator configurator;
            if (configurationContainer.hasComponentDef(Configurator.class)) {
                configurator = (Configurator) configurationContainer
                        .getComponent(Configurator.class);
            } else {
                configurator = new DefaultConfigurator();
            }
            configurator.configure(configurationContainer);
        }
    }

    /**
     * このクラスを破棄します。
     */
    public static void dispose() {
        S2TestClassMethodsRunner.dispose();
        provider = null;
        if (configurationContainer != null) {
            configurationContainer.destroy();
        }
        configurationContainer = null;
        Env.initialize();
    }

    @Override
    public Description getDescription() {
        return delegate.getDescription();
    }

    @Override
    public void run(final RunNotifier notifier) {
        delegate.run(notifier);
    }

    public void filter(final Filter filter) throws NoTestsRemainException {
        filter.apply(delegate);
    }

    public void sort(final Sorter sorter) {
        sorter.apply(delegate);
    }

    /**
     * {@link Seasar2}の振る舞いを構成します。
     * 
     * @author taedium
     */
    public interface Configurator {

        /**
         * Seasar2ランナーの振る舞いを構成します。
         * 
         * @param configurationContainer
         *            コンフィグレーションS2コンテナ
         */
        void configure(S2Container configurationContainer);
    }

    /**
     * {@link Seasar2}の振る舞いを構成するデフォルトの実装クラスです。
     * 
     * @author taedium
     */
    public static class DefaultConfigurator implements Configurator {

        public void configure(final S2Container configurationContainer) {
            if (configurationContainer.hasComponentDef(Provider.class)) {
                provider = (Provider) configurationContainer
                        .getComponent(Provider.class);
            }
            if (configurationContainer
                    .hasComponentDef(S2TestClassMethodsRunner.Provider.class)) {
                S2TestClassMethodsRunner
                        .setProvider((S2TestClassMethodsRunner.Provider) configurationContainer
                                .getComponent(S2TestClassMethodsRunner.Provider.class));
            }
        }
    }

    /**
     * {@link Seasar2}の振る舞いを提供します。
     * 
     * @author taedium
     */
    public interface Provider {

        /**
         * テストクラスランナーを返します。
         * 
         * @param clazz
         *            テストクラス
         * @return テストクラスランナー
         * @throws Exception
         *             何らかの例外が発生した場合
         */
        Runner createTestClassRunner(Class<?> clazz) throws Exception;
    }

    /**
     * {@link Seasar2}の振る舞いを提供するデフォルトの実装クラスです。
     * 
     * @author taedium
     */
    public static class DefaultProvider implements Provider {

        public Runner createTestClassRunner(final Class<?> clazz)
                throws Exception {

            if (hasParameterAnnotation(clazz)) {
                return new S2Parameterized(clazz);
            }
            return new S2TestClassRunner(clazz, new S2TestClassMethodsRunner(
                    clazz));
        }

        /**
         * {@link Parameters}が注釈されたメソッドが存在する場合<code>true</code>
         * 
         * @param clazz
         *            テストクラス
         * @return <code>Parameters</code>が注釈されたメソッドが存在する場合<code>true</code>
         *         、そうでない場合<code>false</code>
         */
        protected boolean hasParameterAnnotation(final Class<?> clazz) {
            for (Method each : clazz.getMethods()) {
                if (each.isBridge() || each.isSynthetic()) {
                    continue;
                }
                if (Modifier.isStatic(each.getModifiers())) {
                    Annotation[] annotations = each.getAnnotations();
                    for (Annotation annotation : annotations) {
                        if (annotation.annotationType() == Parameters.class)
                            return true;
                    }
                }
            }
            return false;
        }
    }
}
