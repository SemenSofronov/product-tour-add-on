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
    protected org.vaadin.addons.producttour.button.StepButton stepButton;

    protected Step step;

    protected List<Consumer<ClickEvent>> consumerList = null;

    public WebStepButton(String caption, String style, Consumer<ClickEvent> consumer) {
        initWebStepButton(caption, style, consumer);
    }

    protected void initWebStepButton(String caption, String style, Consumer<ClickEvent> consumer) {
        stepButton = new org.vaadin.addons.producttour.button.StepButton(caption, style);
        addStepButtonClickListener(consumer);
    }

    @Override
    public <X> X getStepButton() {
        return (X) stepButton;
    }

    @Override
    public void addStepButtonClickListener(Consumer<ClickEvent> consumer) {
        if (consumerList == null) {
            consumerList = new ArrayList<>();

            this.stepButtonClickListener = (org.vaadin.addons.producttour.button.StepButtonClickListener) event -> {
                Component.MouseEventDetails details = new Component.MouseEventDetails();
                details.setClientX(event.getClientX());
                details.setClientY(event.getClientY());
                details.setRelativeY(event.getRelativeY());
                details.setRelativeX(event.getRelativeX());
                ClickEvent e = new ClickEvent(WebStepButton.this, details);
                for (Consumer<ClickEvent> clickEventConsumer : consumerList) {
                    clickEventConsumer.accept(e);
                }
            };
            stepButton.addClickListener(this.stepButtonClickListener);
        }

        if (!consumerList.contains(consumer)) {
            consumerList.add(consumer);
        }
    }

    @Override
    public void removeStepButtonClickListener(Consumer<ClickEvent> consumer) {
        if (consumerList.contains(consumer)) {
            consumerList.remove(consumer);
        }

        if (consumerList.isEmpty()) {
            consumerList = null;
            stepButton.removeClickListener(this.stepButtonClickListener);
            this.stepButtonClickListener = null;
        }
    }


    @Override
    public Step getStep() {
        return step;
    }

    @Override
    public void setStep(Step step) {
        WebStep webStep = (WebStep) step;
        stepButton.setStep(webStep.getStep());
        this.step = webStep;
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
