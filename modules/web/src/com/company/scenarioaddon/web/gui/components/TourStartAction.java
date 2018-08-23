/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.security.app.UserSettingService;
import org.springframework.context.annotation.Scope;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * Standard action to start a tour.
 * Use {@code create()} static methods instead of constructors when creating the action programmatically.
 */
@org.springframework.stereotype.Component("cuba_TourStartAction")
@Scope("prototype")
public class TourStartAction extends BaseAction {

    public static final String ACTION_ID = "tourStart";

    protected Tour tour;

    protected String userSetting;

    @Inject
    protected UserSettingService userSettingService;

    /**
     * Creates an action with the given id and tour.
     *
     * @param id   action's identifier
     * @param tour the tour to start
     * @return the action
     */
    public static TourStartAction create(String id, Tour tour) {
        return AppBeans.getPrototype("cuba_TourStartAction", id, tour);
    }

    /**
     * Creates an action with the given id, tour and shortcut.
     *
     * @param id       action's identifier
     * @param shortcut the shortcut to start the tour
     * @param tour     the tour to start
     * @return the action
     */
    public static TourStartAction create(String id, @Nullable String shortcut, Tour tour) {
        return AppBeans.getPrototype("cuba_TourStartAction", id, shortcut, tour);
    }

    /**
     * Creates an action with the given id, tour, shortcut and user setting.
     *
     * @param id          action's identifier
     * @param shortcut    the shortcut to start the tour
     * @param tour        the tour to start
     * @param userSetting the setting that affects the launch of the tour
     * @return the action
     */
    public static TourStartAction create(String id, @Nullable String shortcut, Tour tour, String userSetting) {
        return AppBeans.getPrototype("cuba_TourStartAction", id, shortcut, tour, userSetting);
    }

    /**
     * Construct an action with given id and tour.
     *
     * @param id   action's identifier
     * @param tour the tour to start
     */
    public TourStartAction(String id, Tour tour) {
        super(id);
        this.tour = tour;
    }

    /**
     * Construct an action with given id, tour and shortcut.
     *
     * @param id       action's identifier
     * @param shortcut the shortcut to start the tour
     * @param tour     the tour to start
     */
    protected TourStartAction(String id, @Nullable String shortcut, Tour tour) {
        super(id, shortcut);
        this.tour = tour;
    }

    /**
     * Construct an action with given id and tour.
     *
     * @param id          action's identifier
     * @param shortcut    the shortcut to start the tour
     * @param tour        the tour to start
     * @param userSetting the setting that affects the launch of the tour
     */
    protected TourStartAction(String id, @Nullable String shortcut, Tour tour, String userSetting) {
        super(id, shortcut);
        this.tour = tour;
        this.userSetting = userSetting;
    }

    /**
     * This method is invoked by the action owner component.
     *
     * @param component component invoking the action
     */
    @Override
    public void actionPerform(Component component) {
        if (userSetting != null) {
            String actionPerformed = userSettingService.loadSetting(userSetting);

            if (actionPerformed == null) {

                if (tour.getCurrentStep() != null) {
                    tour.cancel();
                }
                tour.start();

                userSettingService.saveSetting(userSetting, "performed");
            }
        }
    }
}
