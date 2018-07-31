/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import com.haulmont.cuba.gui.components.Component;

import java.util.ArrayList;
import java.util.List;

public class WebStepButton implements StepButton {

    protected org.vaadin.addons.producttour.button.StepButtonClickListener stepButtonClickListener;
    protected org.vaadin.addons.producttour.button.StepButton stepButton;

    protected WebStep webStep;

    protected List<StepButtonClickListener> listenerList = null;

    public WebStepButton(String caption, String style, StepButtonClickListener stepButtonClickListener) {
        stepButton = new org.vaadin.addons.producttour.button.StepButton(caption, style);

        addStepButtonClickListener(stepButtonClickListener);
    }

    @Override
    public void addStepButtonClickListener(StepButtonClickListener stepButtonClickListener) {
        if (listenerList == null) {
            listenerList = new ArrayList<>();

            this.stepButtonClickListener = (org.vaadin.addons.producttour.button.StepButtonClickListener) event -> {
                Component.MouseEventDetails details = new Component.MouseEventDetails();
                details.setClientX(event.getClientX());
                details.setClientY(event.getClientY());
                details.setRelativeY(event.getRelativeY());
                details.setRelativeX(event.getRelativeX());
                StepButtonClickListener.ClickEvent e = new StepButtonClickListener.ClickEvent(WebStepButton.this, details) {
                    @Override
                    public Tour getTour() {
                        return getStep().getTour();
                    }
                };
                for (StepButtonClickListener buttonClickListener : listenerList) {
                    buttonClickListener.onClick(e);
                }
            };
            stepButton.addClickListener(this.stepButtonClickListener);
        }

        if (!listenerList.contains(stepButtonClickListener)) {
            listenerList.add(stepButtonClickListener);
        }
    }

    @Override
    public void removeStepButtonClickListener(StepButtonClickListener stepButtonClickListener) {
        listenerList.remove(stepButtonClickListener);

        if (listenerList.isEmpty()) {
            listenerList = null;
            stepButton.removeClickListener(this.stepButtonClickListener);
        }
    }


    public org.vaadin.addons.producttour.button.StepButton getStepButton() {
        return stepButton;
    }

    @Override
    public Step getStep() {
        return webStep;
    }

    @Override
    public void setStep(Step step) {
        WebStep webStep = (WebStep) step;
        stepButton.setStep(webStep.getStep());
        this.webStep = webStep;
    }

    @Override
    public String getCaption() {
        return stepButton.getCaption();
    }

    @Override
    public void setCaption(String caption) {
        stepButton.setCaption(caption);
    }

    @Override
    public boolean isEnabled() {
        return stepButton.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        stepButton.setEnabled(enabled);
    }

    @Override
    public void addStyleName(String style) {
        stepButton.addStyleName(style);
    }

    @Override
    public void removeStyleName(String style) {
        stepButton.removeStyleName(style);
    }

    @Override
    public String getStyleName() {
        return stepButton.getStyleName();
    }

    @Override
    public void setStyleName(String style) {
        stepButton.setStyleName(style);
    }
}
