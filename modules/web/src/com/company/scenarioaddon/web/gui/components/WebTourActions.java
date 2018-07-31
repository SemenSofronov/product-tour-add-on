/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import org.vaadin.addons.producttour.actions.TourActions;

public class WebTourActions {

    /**
     * Please use directly the static methods.
     */
    private WebTourActions() {
        // Prevent instantiation
    }

    public static void back(com.company.scenarioaddon.web.gui.components.TourProvider provider) {
        TourActions.back(() -> ((WebTour) provider.getTour()).getTour());
    }

    public static void cancel(com.company.scenarioaddon.web.gui.components.TourProvider provider) {
        TourActions.cancel(() -> ((WebTour) provider.getTour()).getTour());
    }

    public static void hide(com.company.scenarioaddon.web.gui.components.TourProvider provider) {
        TourActions.hide(() -> ((WebTour) provider.getTour()).getTour());
    }

    public static void next(com.company.scenarioaddon.web.gui.components.TourProvider provider) {
        TourActions.next(() -> ((WebTour) provider.getTour()).getTour());
    }

    public static void start(com.company.scenarioaddon.web.gui.components.TourProvider provider) {
        TourActions.start(() -> ((WebTour) provider.getTour()).getTour());
    }
}
