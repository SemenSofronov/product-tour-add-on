/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import java.util.Optional;

public class TourActions {

    /**
     * Please use directly the static methods.
     */
    private TourActions() {
        // Prevent instantiation
    }

    public static void back(com.company.scenarioaddon.web.gui.components.TourProvider provider) {
        Optional.ofNullable(provider.getTour()).ifPresent(Tour::back);
    }

    public static void cancel(com.company.scenarioaddon.web.gui.components.TourProvider provider) {
        Optional.ofNullable(provider.getTour()).ifPresent(Tour::cancel);
    }

    public static void hide(com.company.scenarioaddon.web.gui.components.TourProvider provider) {
        Optional.ofNullable(provider.getTour()).ifPresent(Tour::hide);
    }

    public static void next(com.company.scenarioaddon.web.gui.components.TourProvider provider) {
        Optional.ofNullable(provider.getTour()).ifPresent(Tour::next);
    }

    public static void start(com.company.scenarioaddon.web.gui.components.TourProvider provider) {
        Optional.ofNullable(provider.getTour()).ifPresent(Tour::start);
    }
}
