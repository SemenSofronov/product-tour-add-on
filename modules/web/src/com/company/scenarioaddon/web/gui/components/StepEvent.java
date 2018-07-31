/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

public class StepEvent implements TourProvider, StepProvider {

    protected Object source;

    public StepEvent(Object source) {
        this.source = source;
    }

    @Override
    public Step getStep() {
        return null;
    }

    @Override
    public Tour getTour() {
        return null;
    }
}
