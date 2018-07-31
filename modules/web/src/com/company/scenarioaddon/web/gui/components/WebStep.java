/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import com.haulmont.cuba.gui.components.Component;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import org.vaadin.addons.producttour.step.StepCancelListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.company.scenarioaddon.web.gui.components.WebWrapperUtils.*;


public class WebStep implements Step, Sizeable {

    protected org.vaadin.addons.producttour.step.Step step;

    protected WebTour tour;

    protected HashMap<org.vaadin.addons.producttour.button.StepButton, WebStepButton> buttonMap = new HashMap<>();

    protected List<StepCancelListener> stepCancelListeners = null;
    protected List<StepCompleteListener> stepCompleteListeners = null;
    protected List<StepHideListener> stepHideListeners = null;
    protected List<StepShowListener> stepShowListeners = null;

    protected org.vaadin.addons.producttour.step.StepCancelListener stepCancelListener;
    protected org.vaadin.addons.producttour.step.StepCompleteListener stepCompleteListener;
    protected org.vaadin.addons.producttour.step.StepHideListener stepHideListener;
    protected org.vaadin.addons.producttour.step.StepShowListener stepShowListener;

    protected Component attachedTo;

    public WebStep(String id, com.haulmont.cuba.gui.components.Component attachTo, StepAnchor anchor) {
        AbstractComponent abstractComponent = attachTo.unwrap(AbstractComponent.class);
        step = new org.vaadin.addons.producttour.step.Step(id, abstractComponent, WebWrapperUtils.toVaadinStepAnchor(anchor));
        attachedTo = attachTo;
        step.setSizeFull();
    }

    public org.vaadin.addons.producttour.step.Step getStep() {
        return step;
    }

    @Override
    public void setTour(Tour tour) {
        WebTour webTour = (WebTour) tour;
        step.setTour(webTour.getTour());
        this.tour = webTour;
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
    public void setHeight(float height, Sizeable.Unit unit) {
        step.setHeight(height, unit);
    }

    @Override
    public void setWidth(String width) {
        step.setWidth(width);
    }

    @Override
    public float getWidth() {
        return step.getWidth();
    }

    @Override
    public float getHeight() {
        return step.getHeight();
    }

    @Override
    public Unit getWidthUnits() {
        return step.getWidthUnits();
    }

    @Override
    public Unit getHeightUnits() {
        return step.getHeightUnits();
    }

    @Override
    public void setHeight(String height) {
        step.setHeight(height);
    }

    @Override
    public void setWidth(float width, Sizeable.Unit unit) {
        step.setWidth(width, unit);
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
    public List<WebStepButton> getButtons() {
        return new ArrayList<>(buttonMap.values());
    }

    @Override
    public WebStepButton getButtonByIndex(int index) {
        org.vaadin.addons.producttour.button.StepButton buttonByIndex = step.getButtonByIndex(index);
        return buttonMap.get(buttonByIndex);
    }

    @Override
    public int getButtonCount() {
        return buttonMap.size();
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
    public void setWidthUndefined() {
        step.setWidthUndefined();
    }

    @Override
    public void setHeightUndefined() {
        step.setHeightUndefined();
    }

    @Override
    public Tour getTour() {
        return tour;
    }

    @Override
    public void addButton(StepButton button) {
        WebStepButton webStepButton = (WebStepButton) button;
        webStepButton.setStep(this);
        buttonMap.put(webStepButton.getStepButton(), webStepButton);
        step.addButton(webStepButton.getStepButton());
    }

    @Override
    public void removeButton(StepButton button) {
        WebStepButton webStepButton = (WebStepButton) button;
        webStepButton.setStep(null);
        buttonMap.remove(webStepButton.getStepButton());
        step.removeButton(webStepButton.getStepButton());
    }

    @Override
    public void addCancelListener(StepCancelListener listener) {
        if (stepCancelListeners == null) {
            stepCancelListeners = new ArrayList<>();

            this.stepCancelListener = (org.vaadin.addons.producttour.step.StepCancelListener) event -> {
                StepCancelListener.CancelEvent e = new StepCancelListener.CancelEvent(WebStep.this);
                for (StepCancelListener stepCancelListener: stepCancelListeners) {
                    stepCancelListener.onCancel(e);
                }
            };

            step.addCancelListener(this.stepCancelListener);

        }
        if (!stepCancelListeners.contains(listener)) {
            stepCancelListeners.add(listener);
        }
    }

    @Override
    public void removeCancelListener(StepCancelListener listener) {
        stepCancelListeners.remove(listener);

        if (stepCancelListeners.isEmpty()) {
            stepCancelListeners = null;
            step.removeCancelListener(this.stepCancelListener);
        }
    }

    @Override
    public void addCompleteListener(StepCompleteListener listener) {
        if (stepCompleteListeners == null) {
            stepCompleteListeners = new ArrayList<>();

            this.stepCompleteListener = (org.vaadin.addons.producttour.step.StepCompleteListener) event -> {
                StepCompleteListener.CompleteEvent e = new StepCompleteListener.CompleteEvent(WebStep.this);
                for (StepCompleteListener stepCompleteListener: stepCompleteListeners) {
                    stepCompleteListener.onComplete(e);
                }
            };

            step.addCompleteListener(this.stepCompleteListener);

        }
        if (!stepCompleteListeners.contains(listener)) {
            stepCompleteListeners.add(listener);
        }
    }

    @Override
    public void removeCompleteListener(StepCompleteListener listener) {
        stepCompleteListeners.remove(listener);

        if (stepCompleteListeners.isEmpty()) {
            stepCompleteListeners = null;
            step.removeCompleteListener(this.stepCompleteListener);
        }
    }

    @Override
    public void addHideListener(StepHideListener listener) {
        if (stepHideListeners == null) {
            stepHideListeners = new ArrayList<>();

            this.stepHideListener = (org.vaadin.addons.producttour.step.StepHideListener) event -> {
                StepHideListener.HideEvent e = new StepHideListener.HideEvent(WebStep.this);
                for (StepHideListener stepHideListener: stepHideListeners) {
                    stepHideListener.onHide(e);
                }
            };

            step.addHideListener(this.stepHideListener);

        }
        if (!stepHideListeners.contains(listener)) {
            stepHideListeners.add(listener);
        }
    }

    @Override
    public void removeHideListener(StepHideListener listener) {
        stepHideListeners.remove(listener);

        if (stepHideListeners.isEmpty()) {
            stepHideListeners = null;
            step.removeHideListener(this.stepHideListener);
        }
    }

    @Override
    public void addShowListener(StepShowListener listener) {
        if (stepShowListeners == null) {
            stepShowListeners = new ArrayList<>();

            this.stepShowListener = (org.vaadin.addons.producttour.step.StepShowListener) event -> {
                StepShowListener.ShowEvent e = new StepShowListener.ShowEvent(WebStep.this);
                for (StepShowListener stepShowListener: stepShowListeners) {
                    stepShowListener.onShow(e);
                }
            };

            step.addShowListener(this.stepShowListener);

        }
        if (!stepShowListeners.contains(listener)) {
            stepShowListeners.add(listener);
        }
    }

    @Override
    public void removeShowListener(StepShowListener listener) {
        stepShowListeners.remove(listener);

        if (stepShowListeners.isEmpty()) {
            stepShowListeners = null;
            step.removeShowListener(this.stepShowListener);
        }
    }

}
