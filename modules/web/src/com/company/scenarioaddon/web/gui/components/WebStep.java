/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import com.haulmont.bali.util.Preconditions;
import com.haulmont.cuba.gui.components.Component;
import com.vaadin.ui.AbstractComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;


public class WebStep implements Step {

    protected org.vaadin.addons.producttour.step.Step step;

    protected Tour tour;

    protected List<StepButton> buttonList = new ArrayList<>();

    protected List<Consumer<CancelEvent>> stepCancelListeners = null;
    protected List<Consumer<CompleteEvent>> stepCompleteListeners = null;
    protected List<Consumer<HideEvent>> stepHideListeners = null;
    protected List<Consumer<ShowEvent>> stepShowListeners = null;

    protected org.vaadin.addons.producttour.step.StepCancelListener stepCancelListener;
    protected org.vaadin.addons.producttour.step.StepCompleteListener stepCompleteListener;
    protected org.vaadin.addons.producttour.step.StepHideListener stepHideListener;
    protected org.vaadin.addons.producttour.step.StepShowListener stepShowListener;

    protected Component attachedTo;

    public WebStep(String id, com.haulmont.cuba.gui.components.Component attachTo, StepAnchor anchor) {
        initWebStep(id, attachTo, anchor);
    }

    protected void initWebStep(String id, Component attachTo, StepAnchor anchor) {
        AbstractComponent abstractComponent = null;
        if (attachTo != null) {
            abstractComponent = attachTo.unwrap(AbstractComponent.class);
        }
        org.vaadin.addons.producttour.shared.step.StepAnchor stepAnchor = org.vaadin.addons.producttour.shared.step.StepAnchor.RIGHT;
        if (anchor != null) {
            stepAnchor =  toVaadinStepAnchor(anchor);
        }
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        step = new org.vaadin.addons.producttour.step.Step(id, abstractComponent, stepAnchor);
        attachedTo = attachTo;
        step.setSizeFull();
    }

    public WebStep(com.haulmont.cuba.gui.components.Component attachTo, StepAnchor anchor) {
        initWebStep(null, attachTo, anchor);
    }

    public WebStep(String id, com.haulmont.cuba.gui.components.Component attachTo) {
        initWebStep(id, attachTo, null);
    }

    public WebStep(com.haulmont.cuba.gui.components.Component attachTo) {
        initWebStep(null, attachTo, null);
    }

    public WebStep(String id) {
        initWebStep(id, null, null);
    }

    public WebStep() {
        initWebStep(null, null, null);
    }

    @Override
    public Tour getTour() {
        return tour;
    }

    @Override
    public <X> X getStep() {
        return (X) step;
    }

    @Override
    public void setTour(Tour tour) {
        step.setTour(tour.getTour());
        this.tour = tour;
    }

    @Override
    public void setTitle(String title) {
        step.setTitle(title);
    }

    @Override
    public String getTitle() {
        return step.getTitle();
    }

    @Override
    public void setText(String text) {
        step.setText(text);
    }

    @Override
    public String getText() {
        return step.getText();
    }

    @Override
    public void setSizeFull() {
        step.setSizeFull();
    }

    @Override
    public boolean isVisible() {
        return step.isVisible();
    }

    @Override
    public List<StepButton> getButtons() {
        return Collections.unmodifiableList(buttonList);
    }

    @Override
    public StepButton getButtonByIndex(int index) {
        return buttonList.get(index);
    }

    @Override
    public int getButtonCount() {
        return buttonList.size();
    }

    @Override
    public String getId() {
        return step.getId();
    }

    @Override
    public Component getAttachedTo() {
        return attachedTo;
    }

    @Override
    public void setAttachedTo(Component component) {
        attachedTo = component;
        AbstractComponent abstractComponent = component.unwrap(AbstractComponent.class);
        step.setAttachedTo(abstractComponent);
    }

    @Override
    public void setDetached() {
        attachedTo = null;
        step.setDetached();
    }

    @Override
    public void setCancellable(boolean cancellable) {
        step.setCancellable(cancellable);
    }

    @Override
    public boolean isCancellable() {
        return step.isCancellable();
    }

    @Override
    public void setModal(boolean modal) {
        step.setModal(modal);
    }

    @Override
    public boolean isModal() {
        return step.isModal();
    }

    @Override
    public void setScrollTo(boolean scrollTo) {
        step.setScrollTo(scrollTo);
    }

    @Override
    public boolean isScrollTo() {
        return step.isScrollTo();
    }

    @Override
    public void setTextContentMode(ContentMode contentMode) {
        step.setTextContentMode(toVaadinContentMode(contentMode));
    }

    @Override
    public ContentMode getTextContentMode() {
        return fromVaadinContentMode(step.getTextContentMode());
    }

    @Override
    public void setTitleContentMode(ContentMode contentMode) {
        step.setTextContentMode(toVaadinContentMode(contentMode));
    }

    @Override
    public ContentMode getTitleContentMode() {
        return fromVaadinContentMode(step.getTitleContentMode());
    }

    @Override
    public void setAnchor(StepAnchor anchor) {
        step.setAnchor(toVaadinStepAnchor(anchor));
    }

    @Override
    public StepAnchor getAnchor() {
        return fromVaadinStepAnchor(step.getAnchor());
    }

    @Override
    public void cancel() {
        step.cancel();
    }

    @Override
    public void complete() {
        step.complete();
    }

    @Override
    public void hide() {
        step.hide();
    }

    @Override
    public void show() {
        step.show();
    }

    @Override
    public void scrollTo() {
        step.scrollTo();
    }

    @Override
    public void setSizeUndefined() {
        step.setSizeUndefined();
    }

    @Override
    public void addButton(StepButton button) {
        button.setStep(this);
        buttonList.add(button);
        step.addButton(button.getStepButton());
    }

    @Override
    public void removeButton(StepButton button) {
        button.setStep(null);
        buttonList.remove(button);
        step.removeButton(button.getStepButton());
    }

    @Override
    public void addCancelListener(Consumer<CancelEvent> consumer) {
        if (stepCancelListeners == null) {
            stepCancelListeners = new ArrayList<>();

            this.stepCancelListener = (org.vaadin.addons.producttour.step.StepCancelListener) event -> {
                CancelEvent e = new CancelEvent(WebStep.this);
                for (Consumer<CancelEvent> stepCancelListener: stepCancelListeners) {
                    stepCancelListener.accept(e);
                }
            };

            step.addCancelListener(this.stepCancelListener);

        }
        if (!stepCancelListeners.contains(consumer)) {
            stepCancelListeners.add(consumer);
        }
    }

    @Override
    public void removeCancelListener(Consumer<CancelEvent> consumer) {
        if (stepShowListeners.contains(consumer)) {
            stepCancelListeners.remove(consumer);
        }

        if (stepCancelListeners.isEmpty()) {
            stepCancelListeners = null;
            step.removeCancelListener(this.stepCancelListener);
            this.stepCancelListener = null;
        }
    }

    @Override
    public void addCompleteListener(Consumer<CompleteEvent> consumer) {
        if (stepCompleteListeners == null) {
            stepCompleteListeners = new ArrayList<>();

            this.stepCompleteListener = (org.vaadin.addons.producttour.step.StepCompleteListener) event -> {
                CompleteEvent e = new CompleteEvent(WebStep.this);
                for (Consumer<CompleteEvent> stepCompleteListener: stepCompleteListeners) {
                    stepCompleteListener.accept(e);
                }
            };

            step.addCompleteListener(this.stepCompleteListener);

        }
        if (!stepCompleteListeners.contains(consumer)) {
            stepCompleteListeners.add(consumer);
        }
    }

    @Override
    public void removeCompleteListener(Consumer<CompleteEvent> consumer) {
        if (stepCompleteListeners.contains(consumer)) {
            stepCompleteListeners.remove(consumer);
        }

        if (stepCompleteListeners.isEmpty()) {
            stepCompleteListeners = null;
            step.removeCompleteListener(this.stepCompleteListener);
            this.stepCompleteListener = null;
        }
    }

    @Override
    public void addHideListener(Consumer<HideEvent> consumer) {
        if (stepHideListeners == null) {
            stepHideListeners = new ArrayList<>();

            this.stepHideListener = (org.vaadin.addons.producttour.step.StepHideListener) event -> {
                HideEvent e = new HideEvent(WebStep.this);
                for (Consumer<HideEvent> stepHideListener: stepHideListeners) {
                    stepHideListener.accept(e);
                }
            };

            step.addHideListener(this.stepHideListener);

        }
        if (!stepHideListeners.contains(consumer)) {
            stepHideListeners.add(consumer);
        }
    }

    @Override
    public void removeHideListener(Consumer<HideEvent> consumer) {
        if (stepHideListeners.contains(consumer)) {
            stepHideListeners.remove(consumer);
        }

        if (stepHideListeners.isEmpty()) {
            stepHideListeners = null;
            step.removeHideListener(this.stepHideListener);
            this.stepHideListener = null;
        }
    }

    @Override
    public void addShowListener(Consumer<ShowEvent> consumer) {
        if (stepShowListeners == null) {
            stepShowListeners = new ArrayList<>();

            this.stepShowListener = (org.vaadin.addons.producttour.step.StepShowListener) event -> {
                ShowEvent e = new ShowEvent(WebStep.this);
                for (Consumer<ShowEvent> stepShowListener: stepShowListeners) {
                    stepShowListener.accept(e);
                }
            };

            step.addShowListener(this.stepShowListener);

        }
        if (!stepShowListeners.contains(consumer)) {
            stepShowListeners.add(consumer);
        }
    }

    @Override
    public void removeShowListener(Consumer<ShowEvent> consumer) {
        if (stepShowListeners.contains(consumer)) {
            stepShowListeners.remove(consumer);
        }

        if (stepShowListeners.isEmpty()) {
            stepShowListeners = null;
            step.removeShowListener(this.stepShowListener);
            this.stepShowListener = null;
        }
    }

    public org.vaadin.addons.producttour.shared.step.StepAnchor toVaadinStepAnchor(Step.StepAnchor stepAnchor) {
        Preconditions.checkNotNullArgument(stepAnchor);

        switch (stepAnchor) {
            case TOP:
                return org.vaadin.addons.producttour.shared.step.StepAnchor.TOP;
            case RIGHT:
                return org.vaadin.addons.producttour.shared.step.StepAnchor.RIGHT;
            case BOTTOM:
                return org.vaadin.addons.producttour.shared.step.StepAnchor.BOTTOM;
            case LEFT:
                return org.vaadin.addons.producttour.shared.step.StepAnchor.LEFT;
            default:
                throw new IllegalArgumentException("Unknown step anchor: " + stepAnchor);
        }
    }

    public Step.StepAnchor fromVaadinStepAnchor(org.vaadin.addons.producttour.shared.step.StepAnchor stepAnchor) {
        Preconditions.checkNotNullArgument(stepAnchor);

        switch (stepAnchor) {
            case TOP:
                return Step.StepAnchor.TOP;
            case RIGHT:
                return Step.StepAnchor.RIGHT;
            case BOTTOM:
                return Step.StepAnchor.BOTTOM;
            case LEFT:
                return Step.StepAnchor.LEFT;
            default:
                throw new IllegalArgumentException("Unknown step anchor: " + stepAnchor);
        }
    }

    public org.vaadin.addons.producttour.shared.step.ContentMode toVaadinContentMode(Step.ContentMode contentMode) {
        Preconditions.checkNotNullArgument(contentMode);

        switch (contentMode) {
            case TEXT:
                return org.vaadin.addons.producttour.shared.step.ContentMode.TEXT;
            case PREFORMATTED:
                return org.vaadin.addons.producttour.shared.step.ContentMode.PREFORMATTED;
            case HTML:
                return org.vaadin.addons.producttour.shared.step.ContentMode.HTML;
            default:
                throw new IllegalArgumentException("Unknown content mode: " + contentMode);
        }
    }

    public Step.ContentMode fromVaadinContentMode(org.vaadin.addons.producttour.shared.step.ContentMode contentMode) {
        Preconditions.checkNotNullArgument(contentMode);

        switch (contentMode) {
            case TEXT:
                return Step.ContentMode.TEXT;
            case PREFORMATTED:
                return Step.ContentMode.PREFORMATTED;
            case HTML:
                return Step.ContentMode.HTML;
            default:
                throw new IllegalArgumentException("Unknown content mode: " + contentMode);
        }
    }
}
