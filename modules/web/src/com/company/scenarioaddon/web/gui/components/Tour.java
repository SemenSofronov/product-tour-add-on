package com.company.scenarioaddon.web.gui.components;

import java.util.EventObject;
import java.util.List;
import java.util.function.Consumer;

/**
 * A tour consisting of one or multiple steps.
 *
 * @see Step
 */
public interface Tour {

    /**
     * Get client specific component instance. Can be used in client module to simplify invocation of underlying API.
     *
     * @param internalClass class of underlying component implementation based on Vaadin
     * @param <X>           type of internal class
     * @return internal client specific component
     */
    <X> X unwrap(Class<X> internalClass);

    /**
     * Add the given step to the tour. Steps will be shown in the order they were added.
     *
     * @param step the step to be added
     */
    void addStep(Step step);

    /**
     * Remove the given step from the tour.
     *
     * @param step the step to be removed
     */
    void removeStep(Step step);

    /**
     * Get the last shown step.
     *
     * @return the last shown step or <code>null</code> if no step is shown
     */
    Step getCurrentStep();

    /**
     * Trigger cancel on the current step, hiding it without advancing. The cancel provider for the
     * tour will be triggered.
     */
    void cancel();

    /**
     * Show the first step and begin the tour. The start provider of the tour will be triggered.
     */
    void start();

    /**
     * Hide the current step. The hide provider for the tour will be triggered.
     */
    void hide();

    /**
     * Show the next step in the order they were added.
     */
    void next();

    /**
     * Show the previous step in the order they were added.
     */
    void back();

    /**
     * Get the count of steps for this tour.
     *
     * @return the count of steps
     */
    int getStepCount();

    /**
     * Get a step by its id.
     *
     * @param stepId the id of the step to get
     * @return the step with the given id or <code>null</code> if no step with the given id exists for
     * this tour
     */
    Step getStepById(String stepId);

    /**
     * Get a step by its index.
     *
     * @param index the index of the step to get
     * @return the step at the given index
     */
    Step getStepByIndex(int index);

    /**
     * Get the steps of the tour.
     *
     * @return the steps of the tour inside an unmodifiable container
     */
    List<Step> getSteps();

    /**
     * Add the given listener to the tour that will be triggered if the tour is shown.
     *
     * @param listener the listener to be added
     */
    void addShowListener(Consumer<ShowEvent> listener);

    /**
     * Remove the given listener from the tour.
     *
     * @param listener the listener to be removed.
     */
    void removeShowListener(Consumer<ShowEvent> listener);

    /**
     * Event class that contains information about showing.
     */
    class ShowEvent extends TourEvent {

        private final Step previousStep;
        private final Step currentStep;

        public ShowEvent(Tour source, Step previousStep, Step currentStep) {
            super(source);
            this.previousStep = previousStep;
            this.currentStep = currentStep;
        }
    }

    /**
     * Add the given listener to the tour that will be triggered if the tour is cancelled.
     *
     * @param listener the listener to be added
     */
    void addCancelListener(Consumer<CancelEvent> listener);

    /**
     * Remove the given listener from the tour.
     *
     * @param listener the listener to be removed.
     */
    void removeCancelListener(Consumer<CancelEvent> listener);

    /**
     * Event class that contains information about cancellation.
     */
    class CancelEvent extends TourEvent {

        public CancelEvent(Tour source) {
            super(source);
        }
    }

    /**
     * Add the given listener to the tour that will be triggered if the tour is completed.
     *
     * @param listener the listener to be added
     */
    void addCompleteListener(Consumer<CompleteEvent> listener);

    /**
     * Remove the given listener from the tour.
     *
     * @param listener the listener to be removed.
     */
    void removeCompleteListener(Consumer<CompleteEvent> listener);

    /**
     * Event class that contains information about completion.
     */
    class CompleteEvent extends TourEvent {

        public CompleteEvent(Tour source) {
            super(source);
        }
    }

    /**
     * Add the given listener to the tour that will be triggered if the tour is hidden.
     *
     * @param listener the listener to be added
     */
    void addHideListener(Consumer<HideEvent> listener);

    /**
     * Remove the given listener from the tour.
     *
     * @param listener the listener to be removed.
     */
    void removeHideListener(Consumer<HideEvent> listener);

    /**
     * Event class that contains information about hiding.
     */
    class HideEvent extends TourEvent {

        public HideEvent(Tour source) {
            super(source);
        }
    }

    /**
     * Add the given listener to the tour that will be triggered if the tour is started.
     *
     * @param listener the listener to be added
     */
    void addStartListener(Consumer<StartEvent> listener);

    /**
     * Remove the given listener from the tour.
     *
     * @param listener the listener to be removed.
     */
    void removeStartListener(Consumer<StartEvent> listener);

    /**
     * Event class that contains information about starting.
     */
    class StartEvent extends TourEvent {

        public StartEvent(Tour source) {
            super(source);
        }
    }

    /**
     * Base class for all events that were caused by a {@link Tour}.
     */
    class TourEvent extends EventObject implements TourProvider {

        /**
         * Construct a new provider.
         *
         * @param source the source of the provider
         */
        public TourEvent(Object source) {
            super(source);
        }

        @Override
        public Tour getTour() {
            return (Tour) getSource();
        }
    }
}