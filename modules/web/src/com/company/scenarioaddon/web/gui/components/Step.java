/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import com.haulmont.cuba.gui.components.Component;

import javax.annotation.Nullable;
import java.util.EventObject;
import java.util.List;
import java.util.function.Consumer;

/**
 * A single step of a tour.
 *
 * @see Tour
 */
public interface Step {

    /**
     * Get client specific component instance. Can be used in client module to simplify invocation of underlying API.
     *
     * @param internalClass class of underlying component implementation based on Vaadin
     * @param <X>           type of internal class
     * @return internal client specific component
     */
    <X> X unwrap(Class<X> internalClass);

    /**
     * Add the step to the given tour.
     * Use {@link Tour#addStep(Step)} instead.
     *
     * @param tour the tour the step should be added to
     */
    void setTour(Tour tour);

    /**
     * Get the tour this step is added to.
     *
     * @return the tour
     */
    Tour getTour();

    void setSizeUndefined();

    void setSizeFull();

    /**
     * Set the title of the step.
     *
     * @param title the title to be set
     */
    void setTitle(String title);

    /**
     * Get the title of the step.
     *
     * @return the title of the step
     */
    String getTitle();

    /**
     * Set the text of the step.
     *
     * @param text the text to be set
     */
    void setText(String text);

    /**
     * Get the text of the step.
     *
     * @return the text of the step
     */
    String getText();

    /**
     * Check if the step is currently visible.
     *
     * @return <code>true</code> if the step is currently visible, <code>false</code> else
     */
    boolean isVisible();

    void setWidth(String width);

    float getWidth();

    void setHeight(String height);

    float getHeight();

    int getHeightUnits();

    int getWidthUnits();

    /**
     * Get the id of the step.
     *
     * @return the id of the step
     */
    String getId();

    /**
     * Get the component the step is attached to.
     *
     * @return the component or <code>null</code> if the step is not attached to any component
     */
    Component getAttachedTo();

    /**
     * Set the component the step should be attached to.
     * <p>
     * If set to <code>null</code>, the step will not be attached and shown in the middle of the
     * screen.
     *
     * @param component the component to attach the step to or <code>null</code>
     * @see #setDetached()
     */
    void setAttachedTo(Component component);

    /**
     * Set the step to be not attached to any component. The step will then be shown in the middle of
     * the screen. This is equal to calling {@link #setAttachedTo(Component)} with
     * <code>null</code> as parameter.
     */
    void setDetached();

    /**
     * Set the cancellable state of the step.
     *
     * @param cancellable <code>true</code> if the step should be cancellable, <code>false</code> else
     */
    void setCancellable(boolean cancellable);

    /**
     * Get the cancellable state of the step.
     *
     * @return <code>true</code> if the step is cancellable, <code>false</code> else
     */
    boolean isCancellable();

    /**
     * Set the modality of the step.
     *
     * @param modal <code>true</code> if the step should be modal, <code>false</code> else
     */
    void setModal(boolean modal);

    /**
     * Get the modal state of the step.
     *
     * @return <code>true</code> if the step is modal, <code>false</code> else
     */
    boolean isModal();

    /**
     * Set the scrollTo state of the step.
     *
     * @param scrollTo <code>true</code> if the step should be scrolled into view when shown, <code>false</code>
     *                 else
     */
    void setScrollTo(boolean scrollTo);

    /**
     * Get the scrollTo state of the step.
     *
     * @return <code>true</code> if the step is scrolled to when shown, <code>false</code> else
     */
    boolean isScrollTo();

    /**
     * Sets the content mode for the text of the step.
     *
     * @param contentMode the content mode to be set
     */
    void setTextContentMode(ContentMode contentMode);

    /**
     * Get the content mode for the text of the step.
     *
     * @return the content mode for the text of the step
     */
    ContentMode getTextContentMode();

    /**
     * Sets the content mode for the title of the step.
     *
     * @param contentMode the content mode to be set
     */
    void setTitleContentMode(ContentMode contentMode);

    /**
     * Get the content mode for the title of the step.
     *
     * @return the content mode for the title of the step
     */
    ContentMode getTitleContentMode();

    /**
     * Set the anchor the step is shown relative to the component it is attached to.
     *
     * @param anchor the anchor to be set
     */
    void setAnchor(StepAnchor anchor);

    /**
     * Get the anchor the step is shown relative to the component it is attached to.
     *
     * @return the anchor of the step
     */
    StepAnchor getAnchor();

    /**
     * Get the buttons of the step.
     *
     * @return the buttons of the step inside an unmodifiable container
     */
    List<StepButton> getButtons();

    /**
     * Get a button by its index.
     *
     * @param index the index of the button to get
     * @return the button at the given index
     */
    StepButton getButtonByIndex(int index);

    /**
     * Get the count of buttons of this step.
     *
     * @return the count of buttons of this step
     */
    int getButtonCount();

    /**
     * Add a button the step. The button will be shown in the order they are added.
     *
     * @param button the button to be added
     */
    void addButton(StepButton button);

    /**
     * Remove a button from the step.
     *
     * @param button the button to be removed
     */
    void removeButton(StepButton button);

    /**
     * Hide this step and trigger the cancel provider.
     */
    void cancel();

    /**
     * Hide this step and trigger the complete provider.
     */
    void complete();

    /**
     * Hide this step.
     */
    void hide();

    /**
     * Show this step.
     */
    void show();

    /**
     * Scroll to this steps element.
     */
    void scrollTo();

    /**
     * Contains possible anchors of a step.
     */
    enum StepAnchor {
        TOP("top"),
        RIGHT("right"),
        BOTTOM("bottom"),
        LEFT("left");

        private String id;

        StepAnchor(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        @Nullable
        public static Step.StepAnchor fromId(String id) {
            Step.StepAnchor[] values = Step.StepAnchor.values();
            for (Step.StepAnchor anchor : values) {
                if (anchor.getId().equals(id)) {
                    return anchor;
                }
            }
            return null;

        }
    }

    /**
     * Contains possible content mode of step text and title.
     */
    enum ContentMode {

        /**
         * Content mode, where the step contains only plain text.
         */
        TEXT("text"),

        /**
         * Content mode, where the step contains preformatted text. In this mode
         * newlines are preserved when rendered on the screen.
         */
        PREFORMATTED("preformatted"),

        /**
         * Content mode, where the step contains HTML.
         */
        HTML("html");

        private String id;

        ContentMode(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        @Nullable
        public static Step.ContentMode fromId(String id) {
            Step.ContentMode[] values = Step.ContentMode.values();
            for (Step.ContentMode mode : values) {
                if (mode.getId().equals(id)) {
                    return mode;
                }
            }
            return null;

        }
    }

    /**
     * Add the given listener to the step that will be triggered if the step is cancelled.
     *
     * @param listener the listener to be added
     */
    void addCancelListener(Consumer<CancelEvent> listener);

    /**
     * Remove the given listener from the step.
     *
     * @param listener the listener to be removed.
     */
    void removeCancelListener(Consumer<CancelEvent> listener);

    /**
     * Event class that contains information about cancellation.
     */
    class CancelEvent extends StepEvent {

        public CancelEvent(Step source) {
            super(source);
        }
    }

    /**
     * Add the given listener to the step that will be triggered if the step is completed.
     *
     * @param listener the listener to be added
     */
    void addCompleteListener(Consumer<CompleteEvent> listener);

    /**
     * Remove the given listener from the step.
     *
     * @param listener the listener to be removed.
     */
    void removeCompleteListener(Consumer<CompleteEvent> listener);

    /**
     * Event class that contains information about completion.
     */
    class CompleteEvent extends StepEvent {

        public CompleteEvent(Step source) {
            super(source);
        }
    }

    /**
     * Add the given listener to the step that will be triggered if the step is hidden.
     *
     * @param listener the listener to be added
     */
    void addHideListener(Consumer<HideEvent> listener);

    /**
     * Remove the given listener from the step.
     *
     * @param listener the listener to be removed.
     */
    void removeHideListener(Consumer<HideEvent> listener);

    /**
     * Event class that contains information about hiding.
     */
    class HideEvent extends StepEvent {

        public HideEvent(Step source) {
            super(source);
        }
    }

    /**
     * Add the given listener to the step that will be triggered if the step is shown.
     *
     * @param listener the listener to be added
     */
    void addShowListener(Consumer<ShowEvent> listener);

    /**
     * Remove the given listener from the step.
     *
     * @param listener the listener to be removed.
     */
    void removeShowListener(Consumer<ShowEvent> listener);

    /**
     * Event class that contains information about showing.
     */
    class ShowEvent extends StepEvent {

        public ShowEvent(Step source) {
            super(source);
        }
    }

    /**
     * Base class for all events that were caused by a {@link Step}.
     */
    class StepEvent extends EventObject implements TourProvider, StepProvider {

        /**
         * Construct a new provider.
         *
         * @param source the source of the provider
         */
        public StepEvent(Step source) {
            super(source);
        }

        @Override
        public Step getStep() {
            return (Step) getSource();
        }

        @Override
        public Tour getTour() {
            Step step = getStep();
            return step != null ? step.getTour() : null;
        }
    }

}
