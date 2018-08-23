/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import com.vaadin.server.AbstractExtension;

public abstract class WebAbstractExtension<T extends AbstractExtension> {

    public WebAbstractExtension() {
    }

    protected T extension;

    /**
     * Get client specific component instance. Can be used in client module to simplify invocation of underlying API.
     *
     * @param internalClass class of underlying component implementation based on Vaadin
     * @param <X>           type of internal class
     * @return internal client specific component
     */
    public <X> X unwrap(Class<X> internalClass) {
        return internalClass.cast(getExtension());
    }

    /**
     * Get the component extension
     * @return the component extension
     */
    public T getExtension() {
        return extension;
    }

    protected abstract void initExtension(T extension);
}
