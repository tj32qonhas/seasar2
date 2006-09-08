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
package org.seasar.framework.container.customizer;

import java.util.ArrayList;
import java.util.List;

import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.ComponentDef;


/**
 * @author higa
 * 
 */
public class CustomizerChain implements ComponentCustomizer {

    private List customizers = new ArrayList();

    public int getCustomizerSize() {
        return customizers.size();
    }

    public ComponentCustomizer getCustomizer(int index) {
        return (ComponentCustomizer) customizers.get(index);
    }

    public void addCustomizer(ComponentCustomizer customizer) {
        customizers.add(customizer);
    }

    /*
     * @see org.seasar.framework.container.autoregister.ComponentCustomizer#customize(org.seasar.framework.container.ComponentDef)
     */
    public void customize(ComponentDef componentDef) {
        for (int i = 0; i < getCustomizerSize(); ++i) {
            ComponentCustomizer customizer = getCustomizer(i);
            customizer.customize(componentDef);
        }
    }

}