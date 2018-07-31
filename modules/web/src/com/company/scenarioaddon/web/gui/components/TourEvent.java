/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

public class TourEvent implements TourProvider {

    protected Object source;

    public TourEvent(Object source) {
        this.source = source;
    }

    @Override
    public Tour getTour() {
        return null;
    }
}
