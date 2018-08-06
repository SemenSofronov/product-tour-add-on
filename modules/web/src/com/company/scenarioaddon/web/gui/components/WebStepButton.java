/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import com.haulmont.cuba.gui.components.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WebStepButton implements StepButton {

    protected org.vaadin.addons.producttour.button.StepButtonClickListener stepButtonClickListener;
    protected org.vaadin.addons.producttour.button.StepButton stepButtonExtension;

    protected Step stepExtended;

    protected List<Consumer<ClickEvent>> listenerList = null;

    public WebStepButton(String caption, String style, Consumer<ClickEvent> consumer) {
        createExtension(caption, style);
        addStepButtonClickListener(consumer);
    }

    protected void createExtension(String caption, String style) {
        stepButtonExtension = new org.vaadin.addons.producttour.button.StepButton(caption, style);
    }

    @Override
    public void addStepButtonClickListener(Consumer<ClickEvent> consumer) {
        if (listenerList == null) {
            listenerList = new ArrayList<>();

            this.stepButtonClickListener = (org.vaadin.addons.producttour.button.StepButtonClickListener) event -> {
                Component.MouseEventDetails details = new Component.MouseEventDetails();
                details.setClientX(event.getClientX());
                details.setClientY(event.getClientY());
                details.setRelativeY(event.getRelativeY());
                details.setRelativeX(event.getRelativeX());
                ClickEvent e = new ClickEvent(WebStepButton.this, details);
                for (Consumer<ClickEvent> clickEventConsumer : listenerList) {
                    clickEventConsumer.accept(e);
                }
            };
            stepButtonExtension.addClickListener(this.stepButtonClickListener);
        }

        if (!listenerList.contains(consumer)) {
            listenerList.add(consumer);
        }
    }

    @Override
    public void removeStepButtonClickListener(Consumer<ClickEvent> consumer) {
        if (listenerList.contains(consumer)) {
            listenerList.remove(consumer);
        }

        if (listenerList.isEmpty()) {
            listenerList = null;
            stepButtonExtension.removeClickListener(this.stepButtonClickListener);
            this.stepButtonClickListener = null;
        }
    }


    @Override
    public <X> X getStepButton(Class<X> internalClass) {
        return internalClass.cast(stepButtonExtension);
    }

    @Override
    public Step getStepExtended() {
        return stepExtended;
    }

    @Override
    public void setStepExtended(Step stepExtended) {
        org.vaadin.addons.producttour.step.Step vaadinStep = stepExtended.getStep(org.vaadin.addons.producttour.step.Step.class);
        stepButtonExtension.setStep(vaadinStep);
        this.stepExtended = stepExtended;
    }

    @Override
    public String getCaption() {
        return stepButtonExtension.getCaption();
    }

    @Override
    public void setCaption(String caption) {
        stepButtonExtension.setCaption(caption);
    }

    @Override
    public boolean isEnabled() {
        return stepButtonExtension.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        stepButtonExtension.setEnabled(enabled);
    }

    @Override
    public void addStyleName(String style) {
        stepButtonExtension.addStyleName(style);
    }

    @Override
    public void removeStyleName(String style) {
        stepButtonExtension.removeStyleName(style);
    }

    @Override
    public String getStyleName() {
        return stepButtonExtension.getStyleName();
    }

    @Override
    public void setStyleName(String style) {
        stepButtonExtension.setStyleName(style);
    }
}
