package com.company.scenarioaddon.web.gui.components;

import java.util.Optional;

public enum StepActionType {

    CANCEL("cancel") {
        @Override
        public void execute(StepProvider provider) {
            Optional.ofNullable(provider.getStep()).ifPresent(Step::cancel);
        }
    },

    COMPLETE("complete") {
        @Override
        public void execute(StepProvider provider) {
            Optional.ofNullable(provider.getStep()).ifPresent(Step::complete);
        }
    },

    HIDE("hide") {
        @Override
        public void execute(StepProvider provider) {
            Optional.ofNullable(provider.getStep()).ifPresent(Step::hide);
        }
    },

    SCROLLTO("scrollTo") {
        @Override
        public void execute(StepProvider provider) {
            Optional.ofNullable(provider.getStep()).ifPresent(Step::scrollTo);
        }
    },

    SHOW("show") {
        @Override
        public void execute(StepProvider provider) {
            Optional.ofNullable(provider.getStep()).ifPresent(Step::show);
        }
    };

    private String id;

    StepActionType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract void execute(StepProvider provider);
}
