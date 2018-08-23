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
import java.util.function.Consumer;

/**
 * A single step of a tour.
 *
 * @see Tour
 */
public class WebStep extends WebAbstractExtension<org.vaadin.addons.producttour.step.Step> implements Step {

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

    /**
     * Construct a new step with the given id.
     *
     * @param id The id of the step
     */
    public WebStep(String id) {
        Preconditions.checkNotNullArgument(id);
        extension = createExtension(id);
        initExtension(extension);
    }

    /**
     * Create an extension for a vaadin step.
     *
     * @param id The step id
     * @return The vaadin step extension
     */
    protected org.vaadin.addons.producttour.step.Step createExtension(String id) {
        return new org.vaadin.addons.producttour.step.Step(id);
    }

    /**
     * Initialize a step extension.
     *
     * @param extension The step extension
     */
    @Override
    protected void initExtension(org.vaadin.addons.producttour.step.Step extension) {
        extension.setSizeFull();
    }

    /**
     * Get the tour this step is added to.
     *
     * @return The tour
     */
    @Override
    public Tour getTour() {
        return tour;
    }

    /**
     * DO NOT USE!
     * <p>
     * Used internally to add the step to the given tour.
     * Please use {@link Tour#addStep(Step)} instead.
     *
     * @param tour The tour the step should be added to
     */
    @Override
    public void setTour(Tour tour) {
        org.vaadin.addons.producttour.tour.Tour vaadinTour = tour.unwrap(org.vaadin.addons.producttour.tour.Tour.class);
        extension.setTour(vaadinTour);
        this.tour = tour;
    }

    /**
     * Set the title of the step.
     *
     * @param title The title to be set
     */
    @Override
    public void setTitle(String title) {
        extension.setTitle(title);
    }

    /**
     * Get the title of the step.
     *
     * @return The title of the step
     */
    @Override
    public String getTitle() {
        return extension.getTitle();
    }

    /**
     * Set the text of the step.
     *
     * @param text The text to be set
     */
    @Override
    public void setText(String text) {
        extension.setText(text);
    }

    /**
     * Get the text of the step.
     *
     * @return The text of the step
     */
    @Override
    public String getText() {
        return extension.getText();
    }

    /**
     * Check if the step is currently visible.
     *
     * @return <code>true</code> if the step is currently visible, <code>false</code> else
     */
    @Override
    public boolean isVisible() {
        return extension.isVisible();
    }

    @Override
    public void setSizeFull() {
        extension.setSizeFull();
    }

    @Override
    public void setSizeUndefined() {
        extension.setSizeUndefined();
    }

    @Override
    public void setWidth(String width) {
        extension.setWidth(width);
    }

    @Override
    public float getWidth() {
        return extension.getWidth();
    }

    @Override
    public void setHeight(String height) {
        extension.setHeight(height);
    }

    @Override
    public float getHeight() {
        return extension.getHeight();
    }

    @Override
    public int getHeightUnits() {
        return WebAbstractComponent.UNIT_SYMBOLS.indexOf(extension.getHeightUnits());
    }

    @Override
    public int getWidthUnits() {
        return WebAbstractComponent.UNIT_SYMBOLS.indexOf(extension.getWidthUnits());
    }

    /**
     * Get the buttons of the step.
     *
     * @return The buttons of the step inside an unmodifiable container
     */
    @Override
    public List<StepButton> getButtons() {
        return Collections.unmodifiableList(buttonList);
    }

    /**
     * Get a button by its index.
     *
     * @param index The index of the button to get
     * @return The button at the given index
     */
    @Override
    public StepButton getButtonByIndex(int index) {
        return buttonList.get(index);
    }

    /**
     * Get the count of buttons of this step.
     *
     * @return The count of buttons of this step
     */
    @Override
    public int getButtonCount() {
        return buttonList.size();
    }

    /**
     * Get the id of the step.
     *
     * @return The id of the step
     */
    @Override
    public String getId() {
        return extension.getId();
    }

    /**
     * Get the component the step is attached to.
     *
     * @return The component or <code>null</code> if the step is not attached to any component
     */
    @Override
    public Component getAttachedTo() {
        return attachedTo;
    }

    /**
     * Set the component the step should be attached to.
     * <p>
     * If set to <code>null</code>, the step will not be attached and shown in the middle of the
     * screen.
     *
     * @param component The component to attach the step to or <code>null</code>
     * @see #setDetached()
     */
    @Override
    public void setAttachedTo(Component component) {
        attachedTo = component;
        AbstractComponent abstractComponent = component.unwrap(AbstractComponent.class);
        extension.setAttachedTo(abstractComponent);
    }

    /**
     * Set the step to be not attached to any component. The step will then be shown in the middle of
     * the screen. This is equal to calling {@link #setAttachedTo(Component)} with
     * <code>null</code> as parameter.
     */
    @Override
    public void setDetached() {
        attachedTo = null;
        extension.setDetached();
    }

    /**
     * Set the cancellable state of the step.
     *
     * @param cancellable <code>true</code> if the step should be cancellable, <code>false</code> else
     */
    @Override
    public void setCancellable(boolean cancellable) {
        extension.setCancellable(cancellable);
    }

    /**
     * Get the cancellable state of the step.
     *
     * @return <code>true</code> if the step is cancellable, <code>false</code> else
     */
    @Override
    public boolean isCancellable() {
        return extension.isCancellable();
    }

    /**
     * Set the modality of the step.
     *
     * @param modal <code>true</code> if the step should be modal, <code>false</code> else
     */
    @Override
    public void setModal(boolean modal) {
        extension.setModal(modal);
    }

    /**
     * Get the modal state of the step.
     *
     * @return <code>true</code> if the step is modal, <code>false</code> else
     */
    @Override
    public boolean isModal() {
        return extension.isModal();
    }

    /**
     * Set the scrollTo state of the step.
     *
     * @param scrollTo <code>true</code> if the step should be scrolled into view when shown, <code>false</code>
     *                 else
     */
    @Override
    public void setScrollTo(boolean scrollTo) {
        extension.setScrollTo(scrollTo);
    }

    /**
     * Get the scrollTo state of the step.
     *
     * @return <code>true</code> if the step is scrolled to when shown, <code>false</code> else
     */
    @Override
    public boolean isScrollTo() {
        return extension.isScrollTo();
    }

    /**
     * Sets the content mode for the text of the step.
     *
     * @param contentMode The content mode to be set
     */
    @Override
    public void setTextContentMode(ContentMode contentMode) {
        extension.setTextContentMode(toVaadinContentMode(contentMode));
    }

    /**
     * Get the content mode for the text of the step.
     *
     * @return The content mode for the text of the step
     */
    @Override
    public ContentMode getTextContentMode() {
        return fromVaadinContentMode(extension.getTextContentMode());
    }

    /**
     * Sets the content mode for the title of the step.
     *
     * @param contentMode The content mode to be set
     */
    @Override
    public void setTitleContentMode(ContentMode contentMode) {
        extension.setTitleContentMode(toVaadinContentMode(contentMode));
    }

    /**
     * Get the content mode for the title of the step.
     *
     * @return The content mode for the title of the step
     */
    @Override
    public ContentMode getTitleContentMode() {
        return fromVaadinContentMode(extension.getTitleContentMode());
    }

    /**
     * Set the anchor the step is shown relative to the component it is attached to.
     *
     * @param anchor The anchor to be set
     */
    @Override
    public void setAnchor(StepAnchor anchor) {
        extension.setAnchor(toVaadinStepAnchor(anchor));
    }

    /**
     * Get the anchor the step is shown relative to the component it is attached to.
     *
     * @return The anchor of the step
     */
    @Override
    public StepAnchor getAnchor() {
        return fromVaadinStepAnchor(extension.getAnchor());
    }

    /**
     * Hide this step and trigger the cancel provider.
     */
    @Override
    public void cancel() {
        extension.cancel();
    }

    /**
     * Hide this step and trigger the complete provider.
     */
    @Override
    public void complete() {
        extension.complete();
    }

    /**
     * Hide this step.
     */
    @Override
    public void hide() {
        extension.hide();
    }

    /**
     * Show this step.
     */
    @Override
    public void show() {
        extension.show();
    }

    /**
     * Scroll to this steps element.
     */
    @Override
    public void scrollTo() {
        extension.scrollTo();
    }

    /**
     * Add a button the step. The button will be shown in the order they are added.
     *
     * @param button The button to be added
     */
    @Override
    public void addButton(StepButton button) {
        button.setStep(this);
        buttonList.add(button);
        org.vaadin.addons.producttour.button.StepButton vaadinStepButton = button.unwrap(org.vaadin.addons.producttour.button.StepButton.class);
        extension.addButton(vaadinStepButton);
    }

    /**
     * Remove a button from the step.
     *
     * @param button The button to be removed
     */
    @Override
    public void removeButton(StepButton button) {
        button.setStep(null);
        buttonList.remove(button);
        org.vaadin.addons.producttour.button.StepButton vaadinStepButton = button.unwrap(org.vaadin.addons.producttour.button.StepButton.class);
        extension.removeButton(vaadinStepButton);
    }

    /**
     * Add the given listener to the step that will be triggered if the step is cancelled.
     *
     * @param cancelListener The listener to be added
     */
    @Override
    public void addCancelListener(Consumer<CancelEvent> cancelListener) {
        if (stepCancelListeners == null) {
            stepCancelListeners = new ArrayList<>();

            this.stepCancelListener = (org.vaadin.addons.producttour.step.StepCancelListener) event -> {
                CancelEvent e = new CancelEvent(WebStep.this);
                for (Consumer<CancelEvent> stepCancelListener : stepCancelListeners) {
                    stepCancelListener.accept(e);
                }
            };

            extension.addCancelListener(this.stepCancelListener);

        }
        if (!stepCancelListeners.contains(cancelListener)) {
            stepCancelListeners.add(cancelListener);
        }
    }

    /**
     * Remove the given listener from the step.
     *
     * @param cancelListener The listener to be removed.
     */
    @Override
    public void removeCancelListener(Consumer<CancelEvent> cancelListener) {
        if (stepCancelListeners != null) {
            stepCancelListeners.remove(cancelListener);

            if (stepCancelListeners.isEmpty()) {
                stepCancelListeners = null;
                extension.removeCancelListener(this.stepCancelListener);
                this.stepCancelListener = null;
            }
        }
    }

    /**
     * Add the given listener to the step that will be triggered if the step is completed.
     *
     * @param completeListener The listener to be added
     */
    @Override
    public void addCompleteListener(Consumer<CompleteEvent> completeListener) {
        if (stepCompleteListeners == null) {
            stepCompleteListeners = new ArrayList<>();

            this.stepCompleteListener = (org.vaadin.addons.producttour.step.StepCompleteListener) event -> {
                CompleteEvent e = new CompleteEvent(WebStep.this);
                for (Consumer<CompleteEvent> stepCompleteListener : stepCompleteListeners) {
                    stepCompleteListener.accept(e);
                }
            };

            extension.addCompleteListener(this.stepCompleteListener);

        }
        if (!stepCompleteListeners.contains(completeListener)) {
            stepCompleteListeners.add(completeListener);
        }
    }

    /**
     * Remove the given listener from the step.
     *
     * @param completeListener The listener to be removed.
     */
    @Override
    public void removeCompleteListener(Consumer<CompleteEvent> completeListener) {
        if (stepCompleteListeners != null) {
            stepCompleteListeners.remove(completeListener);

            if (stepCompleteListeners.isEmpty()) {
                stepCompleteListeners = null;
                extension.removeCompleteListener(this.stepCompleteListener);
                this.stepCompleteListener = null;
            }
        }
    }

    /**
     * Add the given listener to the step that will be triggered if the step is hidden.
     *
     * @param hideListener The listener to be added
     */
    @Override
    public void addHideListener(Consumer<HideEvent> hideListener) {
        if (stepHideListeners == null) {
            stepHideListeners = new ArrayList<>();

            this.stepHideListener = (org.vaadin.addons.producttour.step.StepHideListener) event -> {
                HideEvent e = new HideEvent(WebStep.this);
                for (Consumer<HideEvent> stepHideListener : stepHideListeners) {
                    stepHideListener.accept(e);
                }
            };

            extension.addHideListener(this.stepHideListener);

        }
        if (!stepHideListeners.contains(hideListener)) {
            stepHideListeners.add(hideListener);
        }
    }

    /**
     * Remove the given listener from the step.
     *
     * @param hideListener The listener to be removed.
     */
    @Override
    public void removeHideListener(Consumer<HideEvent> hideListener) {
        if (stepHideListeners != null) {
            stepHideListeners.remove(hideListener);

            if (stepHideListeners.isEmpty()) {
                stepHideListeners = null;
                extension.removeHideListener(this.stepHideListener);
                this.stepHideListener = null;
            }
        }
    }

    /**
     * Add the given listener to the step that will be triggered if the step is shown.
     *
     * @param showListener The listener to be added
     */
    @Override
    public void addShowListener(Consumer<ShowEvent> showListener) {
        if (stepShowListeners == null) {
            stepShowListeners = new ArrayList<>();

            this.stepShowListener = (org.vaadin.addons.producttour.step.StepShowListener) event -> {
                ShowEvent e = new ShowEvent(WebStep.this);
                for (Consumer<ShowEvent> stepShowListener : stepShowListeners) {
                    stepShowListener.accept(e);
                }
            };

            extension.addShowListener(this.stepShowListener);

        }
        if (!stepShowListeners.contains(showListener)) {
            stepShowListeners.add(showListener);
        }
    }

    /**
     * Remove the given listener from the step.
     *
     * @param showListener The listener to be removed.
     */
    @Override
    public void removeShowListener(Consumer<ShowEvent> showListener) {
        if (stepShowListeners != null) {
            stepShowListeners.remove(showListener);

            if (stepShowListeners.isEmpty()) {
                stepShowListeners = null;
                extension.removeShowListener(this.stepShowListener);
                this.stepShowListener = null;
            }
        }
    }

    /**
     * Convert a cuba step anchor to a vaadin step anchor
     *
     * @param stepAnchor The cuba step anchor
     * @return The vaadin step anchor
     */
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
                throw new IllegalArgumentException("Unknown extension anchor: " + stepAnchor);
        }
    }

    /**
     * Convert a vaadin step anchor to a cuba step anchor
     *
     * @param stepAnchor The vaadin step anchor
     * @return The cuba step anchor
     */
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
                throw new IllegalArgumentException("Unknown extension anchor: " + stepAnchor);
        }
    }

    /**
     * Convert a cuba content mode to a vaadin content mode
     *
     * @param contentMode The cuba content mode
     * @return The vaadin content mode
     */
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

    /**
     * Convert a vaadin content mode to a cuba content mode
     *
     * @param contentMode The vaadin content mode
     * @return The cuba content mode
     */
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
