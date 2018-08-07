/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import org.springframework.context.annotation.Scope;

import javax.annotation.Nullable;

@org.springframework.stereotype.Component("cuba_TourStartAction")
@Scope("prototype")
public class TourStartAction extends BaseAction {

    public static final String ACTION_ID = "start";

    protected Tour tour;

    public static TourStartAction create(String id, Tour tour) {
        return AppBeans.getPrototype("cuba_TourStartAction", id, tour);
    }

    public static TourStartAction create(String id, @Nullable String shortcut, Tour tour) {
        return AppBeans.getPrototype("cuba_TourStartAction", id, shortcut, tour);
    }

    public TourStartAction(String id, Tour tour) {
        super(id);
        this.tour = tour;
    }

    protected TourStartAction(String id, @Nullable String shortcut, Tour tour) {
        super(id, shortcut);
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
