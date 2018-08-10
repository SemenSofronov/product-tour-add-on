/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import com.vaadin.server.AbstractExtension;

import javax.annotation.Nullable;

public abstract class WebAbstractExtension<T extends AbstractExtension> {

    protected T extension;

    public <X> X unwrap(Class<X> internalClass) {
        return internalClass.cast(getExtension());
    }

    public T getExtension() {
        return extension;
    }

    protected abstract T createExtension(@Nullable String attribute);

    protected abstract void initExtension(T extension);
}
