package com.company.scenarioaddon.web.gui.components;

import com.haulmont.cuba.gui.components.Component;

import java.util.EventObject;
import java.util.function.Consumer;

public interface StepButton {

    <X> X getStepButton();

    Step getStep();

    void setStep(Step step);

    String getCaption();

    void setCaption(String caption);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void addStyleName(String style);

    void removeStyleName(String style);

    String getStyleName();

    void setStyleName(String style);

    void addStepButtonClickListener(Consumer<ClickEvent> consumer);

    void removeStepButtonClickListener(Consumer<ClickEvent> consumer);

    class ClickEvent extends EventObject implements TourProvider, StepProvider, StepButtonProvider {
        protected Component.MouseEventDetails details;

        public ClickEvent(StepButton source) {
            this(source, null);
        }

        public ClickEvent(StepButton source, Component.MouseEventDetails details) {
            super(source);
            this.details = details;
        }

        public Component.MouseEventDetails getDetails() {
            return details;
        }

        @Override
        public Tour getTour() {
            Step step = getStep();
            return step != null ? step.getTour() : null;
        }

        @Override
        public Step getStep() {
            StepButton button = getStepButton();
            return button != null ? button.getStep() : null;
        }

        @Override
        public StepButton getStepButton() {
            return (StepButton) getSource();
        }
    }
}
