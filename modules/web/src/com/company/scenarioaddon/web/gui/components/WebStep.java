/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import com.haulmont.bali.util.Preconditions;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.web.gui.components.WebAbstractComponent;
import com.vaadin.ui.AbstractComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;


public class WebStep implements Step {

    protected org.vaadin.addons.producttour.step.Step stepExtension;

    protected Tour tourExtended;

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
        createExtension(id, attachTo, anchor);
        setDefualtConfiguration();
        attachedTo = attachTo;
    }

    protected void setDefualtConfiguration() {
        stepExtension.setSizeFull();
    }

    protected void createExtension(String id, Component attachTo, StepAnchor anchor) {
        AbstractComponent abstractComponent = null;
        if (attachTo != null) {
            abstractComponent = attachTo.unwrap(AbstractComponent.class);
        }
        org.vaadin.addons.producttour.shared.step.StepAnchor stepAnchor = org.vaadin.addons.producttour.shared.step.StepAnchor.RIGHT;
        if (anchor != null) {
            stepAnchor = toVaadinStepAnchor(anchor);
        }
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        stepExtension = new org.vaadin.addons.producttour.step.Step(id, abstractComponent, stepAnchor);
    }

    public WebStep(com.haulmont.cuba.gui.components.Component attachTo, StepAnchor anchor) {
        createExtension(null, attachTo, anchor);
        setDefualtConfiguration();
        attachedTo = attachTo;
    }

    public WebStep(String id, com.haulmont.cuba.gui.components.Component attachTo) {
        createExtension(id, attachTo, null);
        setDefualtConfiguration();
        attachedTo = attachTo;
    }

    public WebStep(com.haulmont.cuba.gui.components.Component attachTo) {
        createExtension(null, attachTo, null);
        setDefualtConfiguration();
        attachedTo = attachTo;
    }

    public WebStep(String id) {
        createExtension(id, null, null);
    }

    public WebStep() {
        createExtension(null, null, null);
        setDefualtConfiguration();
    }

    @Override
    public Tour getTourExtended() {
        return tourExtended;
    }

    @Override
    public <X> X getStep(Class<X> internalClass) {
        return internalClass.cast(stepExtension);
    }

    @Override
    public void setTourExtended(Tour tourExtended) {
        org.vaadin.addons.producttour.tour.Tour vaadinTour = tourExtended.getTour(org.vaadin.addons.producttour.tour.Tour.class);
        stepExtension.setTour(vaadinTour);
        this.tourExtended = tourExtended;
    }

    @Override
    public void setTitle(String title) {
        stepExtension.setTitle(title);
    }

    @Override
    public String getTitle() {
        return stepExtension.getTitle();
    }

    @Override
    public void setText(String text) {
        stepExtension.setText(text);
    }

    @Override
    public String getText() {
        return stepExtension.getText();
    }

    @Override
    public void setSizeFull() {
        stepExtension.setSizeFull();
    }

    @Override
    public boolean isVisible() {
        return stepExtension.isVisible();
    }

    @Override
    public void setWidth(String width) {
        stepExtension.setWidth(width);
    }

    @Override
    public float getWidth() {
        return stepExtension.getWidth();
    }

    @Override
    public void setHeight(String height) {
        stepExtension.setHeight(height);
    }

    @Override
    public float getHeight() {
        return stepExtension.getHeight();
    }

    @Override
    public int getHeightUnits() {
        return WebAbstractComponent.UNIT_SYMBOLS.indexOf(stepExtension.getHeightUnits());
    }

    @Override
    public int getWidthUnits() {
        return WebAbstractComponent.UNIT_SYMBOLS.indexOf(stepExtension.getWidthUnits());
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
        return stepExtension.getId();
    }

    @Override
    public Component getAttachedTo() {
        return attachedTo;
    }

    @Override
    public void setAttachedTo(Component component) {
        attachedTo = component;
        AbstractComponent abstractComponent = component.unwrap(AbstractComponent.class);
        stepExtension.setAttachedTo(abstractComponent);
    }

    @Override
    public void setDetached() {
        attachedTo = null;
        stepExtension.setDetached();
    }

    @Override
    public void setCancellable(boolean cancellable) {
        stepExtension.setCancellable(cancellable);
    }

    @Override
    public boolean isCancellable() {
        return stepExtension.isCancellable();
    }

    @Override
    public void setModal(boolean modal) {
        stepExtension.setModal(modal);
    }

    @Override
    public boolean isModal() {
        return stepExtension.isModal();
    }

    @Override
    public void setScrollTo(boolean scrollTo) {
        stepExtension.setScrollTo(scrollTo);
    }

    @Override
    public boolean isScrollTo() {
        return stepExtension.isScrollTo();
    }

    @Override
    public void setTextContentMode(ContentMode contentMode) {
        stepExtension.setTextContentMode(toVaadinContentMode(contentMode));
    }

    @Override
    public ContentMode getTextContentMode() {
        return fromVaadinContentMode(stepExtension.getTextContentMode());
    }

    @Override
    public void setTitleContentMode(ContentMode contentMode) {
        stepExtension.setTextContentMode(toVaadinContentMode(contentMode));
    }

    @Override
    public ContentMode getTitleContentMode() {
        return fromVaadinContentMode(stepExtension.getTitleContentMode());
    }

    @Override
    public void setAnchor(StepAnchor anchor) {
        stepExtension.setAnchor(toVaadinStepAnchor(anchor));
    }

    @Override
    public StepAnchor getAnchor() {
        return fromVaadinStepAnchor(stepExtension.getAnchor());
    }

    @Override
    public void cancel() {
        stepExtension.cancel();
    }

    @Override
    public void complete() {
        stepExtension.complete();
    }

    @Override
    public void hide() {
        stepExtension.hide();
    }

    @Override
    public void show() {
        stepExtension.show();
    }

    @Override
    public void scrollTo() {
        stepExtension.scrollTo();
    }

    @Override
    public void setSizeUndefined() {
        stepExtension.setSizeUndefined();
    }

    @Override
    public void addButton(StepButton button) {
        button.setStepExtended(this);
        buttonList.add(button);
        org.vaadin.addons.producttour.button.StepButton vaadinStepButton = button.getStepButton(org.vaadin.addons.producttour.button.StepButton.class);
        stepExtension.addButton(vaadinStepButton);
    }

    @Override
    public void removeButton(StepButton button) {
        button.setStepExtended(null);
        buttonList.remove(button);
        org.vaadin.addons.producttour.button.StepButton vaadinStepButton = button.getStepButton(org.vaadin.addons.producttour.button.StepButton.class);
        stepExtension.removeButton(vaadinStepButton);
    }

    @Override
    public void addCancelListener(Consumer<CancelEvent> consumer) {
        if (stepCancelListeners == null) {
            stepCancelListeners = new ArrayList<>();

            this.stepCancelListener = (org.vaadin.addons.producttour.step.StepCancelListener) event -> {
                CancelEvent e = new CancelEvent(WebStep.this);
                for (Consumer<CancelEvent> stepCancelListener : stepCancelListeners) {
                    stepCancelListener.accept(e);
                }
            };

            stepExtension.addCancelListener(this.stepCancelListener);

        }
        if (!stepCancelListeners.contains(consumer)) {
            stepCancelListeners.add(consumer);
        }
    }

    @Override
    public void removeCancelListener(Consumer<CancelEvent> consumer) {
        if (stepCancelListeners != null) {
            for (Consumer<CancelEvent> cancelEventConsumer : stepCancelListeners) {
                if (cancelEventConsumer == consumer) {
                    stepCancelListeners.remove(consumer);
                }
            }

            if (stepCancelListeners.isEmpty()) {
                stepCancelListeners = null;
                stepExtension.removeCancelListener(this.stepCancelListener);
                this.stepCancelListener = null;
            }
        }
    }

    @Override
    public void addCompleteListener(Consumer<CompleteEvent> consumer) {
        if (stepCompleteListeners == null) {
            stepCompleteListeners = new ArrayList<>();

            this.stepCompleteListener = (org.vaadin.addons.producttour.step.StepCompleteListener) event -> {
                CompleteEvent e = new CompleteEvent(WebStep.this);
                for (Consumer<CompleteEvent> stepCompleteListener : stepCompleteListeners) {
                    stepCompleteListener.accept(e);
                }
            };

            stepExtension.addCompleteListener(this.stepCompleteListener);

        }
        if (!stepCompleteListeners.contains(consumer)) {
            stepCompleteListeners.add(consumer);
        }
    }

    @Override
    public void removeCompleteListener(Consumer<CompleteEvent> consumer) {
        if (stepCompleteListeners != null) {
            for (Consumer<CompleteEvent> completeEventConsumer : stepCompleteListeners) {
                if (completeEventConsumer == consumer) {
                    stepCompleteListeners.remove(consumer);
                }
            }

            if (stepCompleteListeners.isEmpty()) {
                stepCompleteListeners = null;
                stepExtension.removeCompleteListener(this.stepCompleteListener);
                this.stepCompleteListener = null;
            }
        }
    }

    @Override
    public void addHideListener(Consumer<HideEvent> consumer) {
        if (stepHideListeners == null) {
            stepHideListeners = new ArrayList<>();

            this.stepHideListener = (org.vaadin.addons.producttour.step.StepHideListener) event -> {
                HideEvent e = new HideEvent(WebStep.this);
                for (Consumer<HideEvent> stepHideListener : stepHideListeners) {
                    stepHideListener.accept(e);
                }
            };

            stepExtension.addHideListener(this.stepHideListener);

        }
        if (!stepHideListeners.contains(consumer)) {
            stepHideListeners.add(consumer);
        }
    }

    @Override
    public void removeHideListener(Consumer<HideEvent> consumer) {
        if (stepHideListeners != null) {
            for (Consumer<HideEvent> hideEventConsumer : stepHideListeners) {
                if (hideEventConsumer == consumer) {
                    stepHideListeners.remove(consumer);
                }
            }

            if (stepHideListeners.isEmpty()) {
                stepHideListeners = null;
                stepExtension.removeHideListener(this.stepHideListener);
                this.stepHideListener = null;
            }
        }
    }

    @Override
    public void addShowListener(Consumer<ShowEvent> consumer) {
        if (stepShowListeners == null) {
            stepShowListeners = new ArrayList<>();

            this.stepShowListener = (org.vaadin.addons.producttour.step.StepShowListener) event -> {
                ShowEvent e = new ShowEvent(WebStep.this);
                for (Consumer<ShowEvent> stepShowListener : stepShowListeners) {
                    stepShowListener.accept(e);
                }
            };

            stepExtension.addShowListener(this.stepShowListener);

        }
        if (!stepShowListeners.contains(consumer)) {
            stepShowListeners.add(consumer);
        }
    }

    @Override
    public void removeShowListener(Consumer<ShowEvent> consumer) {
        if (stepShowListeners != null) {
            for (Consumer<ShowEvent> showEventConsumer : stepShowListeners) {
                if (showEventConsumer == consumer) {
                    stepShowListeners.remove(consumer);
                }
            }

            if (stepShowListeners.isEmpty()) {
                stepShowListeners = null;
                stepExtension.removeShowListener(this.stepShowListener);
                this.stepShowListener = null;
            }
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
                throw new IllegalArgumentException("Unknown stepExtension anchor: " + stepAnchor);
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
                throw new IllegalArgumentException("Unknown stepExtension anchor: " + stepAnchor);
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
