/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import com.haulmont.bali.util.Preconditions;
import com.haulmont.cuba.gui.components.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A button of a step that can be used to provide different actions if clicked.
 *
 * @see Step
 */
public class WebStepButton extends WebAbstractExtension<org.vaadin.addons.producttour.button.StepButton> implements StepButton {

    protected org.vaadin.addons.producttour.button.StepButtonClickListener stepButtonClickListener;

    protected Step step;

    protected List<Consumer<ClickEvent>> listenerList = null;

    /**
     * Creates a new button with the given caption.
     *
     * @param caption The caption of the button
     */
    public WebStepButton(String caption) {
        Preconditions.checkNotNullArgument(caption);
        extension = createExtension(caption);
    }

    /**
     * Create an extension for a vaadin step button.
     *
     * @param caption The step button caption
     * @return The vaadin step button extension
     */
    protected org.vaadin.addons.producttour.button.StepButton createExtension(String caption) {
        return new org.vaadin.addons.producttour.button.StepButton(caption);
    }

    /**
     * Initialize a step button extension.
     *
     * @param extension The step button extension
     */
    @Override
    protected void initExtension(org.vaadin.addons.producttour.button.StepButton extension) {
    }

    /**
     * Adds a click listener to the button.
     *
     * @param clickListener The listener to be added
     */
    @Override
    public void addStepButtonClickListener(Consumer<ClickEvent> clickListener) {
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
            extension.addClickListener(this.stepButtonClickListener);
        }

        if (!listenerList.contains(clickListener)) {
            listenerList.add(clickListener);
        }
    }

    /**
     * Remove the given click listener from the button.
     *
     * @param clickListener The listener to be removed
     */
    @Override
    public void removeStepButtonClickListener(Consumer<ClickEvent> clickListener) {
        if (listenerList != null) {
            listenerList.remove(clickListener);

            if (listenerList.isEmpty()) {
                listenerList = null;
                extension.removeClickListener(this.stepButtonClickListener);
                this.stepButtonClickListener = null;
            }
        }
    }

    /**
     * Get the list of the click listeners.
     * @return The list of the click listeners
     */
    @Override
    public List<Consumer<ClickEvent>> getClickListeners() {
        return listenerList;
    }

    /**
     * Get the step the button is attached to.
     *
     * @return The step
     */
    @Override
    public Step getStep() {
        return step;
    }

    /**
     * DO NOT USE!
     * <p>
     * Used internally to add the button to the given step.
     * Please use {@link Step#addButton(StepButton)} instead.
     *
     * @param step The step the button should be added to
     */
    @Override
    public void setStep(Step step) {
        org.vaadin.addons.producttour.step.Step vaadinStep = step.unwrap(org.vaadin.addons.producttour.step.Step.class);
        extension.setStep(vaadinStep);
        this.step = step;
    }

    /**
     * Get the caption of the button
     *
     * @return The caption of the button
     */
    @Override
    public String getCaption() {
        return extension.getCaption();
    }

    /**
     * Set the caption of the button.
     *
     * @param caption The caption to be set
     */
    @Override
    public void setCaption(String caption) {
        extension.setCaption(caption);
    }

    /**
     * Get the enabled state of the button.
     *
     * @return <code>true</code> if the button is enabled, <code>false</code> else
     */
    @Override
    public boolean isEnabled() {
        return extension.isEnabled();
    }

    /**
     * Set the enabled state of the button.
     *
     * @param enabled The enabled state to be set
     */
    @Override
    public void setEnabled(boolean enabled) {
        extension.setEnabled(enabled);
    }

    /**
     * @see Component#addStyleName(String)
     */
    @Override
    public void addStyleName(String style) {
        extension.addStyleName(style);
    }

    /**
     * @see Component#removeStyleName(String)
     */
    @Override
    public void removeStyleName(String style) {
        extension.removeStyleName(style);
    }

    /**
     * @see Component#getStyleName()
     */
    @Override
    public String getStyleName() {
        return extension.getStyleName();
    }

    /**
     * @see Component#setStyleName(String)
     */
    @Override
    public void setStyleName(String style) {
        extension.setStyleName(style);
    }
}
