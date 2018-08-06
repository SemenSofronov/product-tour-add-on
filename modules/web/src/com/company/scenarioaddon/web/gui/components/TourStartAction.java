/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import com.haulmont.cuba.gui.components.AbstractAction;
import com.haulmont.cuba.gui.components.Component;

import javax.annotation.Nullable;

public class TourStartAction extends AbstractAction {

    protected Tour tour;

    public TourStartAction(String id, Tour tour) {
        super(id);
        this.tour = tour;
    }

    public TourStartAction(String id, @Nullable String shortcut, Tour tour) {
        super(id, shortcut);
        this.tour = tour;
    }

    public TourStartAction(String id, Status status, Tour tour) {
        super(id, status);
        this.tour = tour;
    }

    @Override
    public void actionPerform(Component component) {
        if (tour.getCurrentStep() != null) {
            tour.cancel();
        }
        tour.start();
    }
}
