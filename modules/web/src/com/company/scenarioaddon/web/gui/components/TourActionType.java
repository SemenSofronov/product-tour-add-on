/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import java.util.Optional;

public enum TourActionType {

    BACK("back") {
        @Override
        public void execute(TourProvider provider) {
            Optional.ofNullable(provider.getTour()).ifPresent(Tour::back);
        }
    },

    CANCEL("cancel") {
        @Override
        public void execute(TourProvider provider) {
            Optional.ofNullable(provider.getTour()).ifPresent(Tour::cancel);
        }
    },

    HIDE("hide") {
        @Override
        public void execute(TourProvider provider) {
            Optional.ofNullable(provider.getTour()).ifPresent(Tour::hide);
        }
    },

    NEXT("next") {
        @Override
        public void execute(TourProvider provider) {
            Optional.ofNullable(provider.getTour()).ifPresent(Tour::next);
        }
    },

    START("start") {
        @Override
        public void execute(TourProvider provider) {
            Optional.ofNullable(provider.getTour()).ifPresent(Tour::start);
        }
    };

    private String id;

    TourActionType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract void execute(TourProvider provider);
}
