package com.company.scenarioaddon.web.gui.components;

import com.haulmont.cuba.gui.components.Component;
import com.vaadin.server.AbstractClientConnector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A tour consisting of one or multiple steps.
 *
 * @see Step
 */
public class WebTour extends WebAbstractExtension<org.vaadin.addons.producttour.tour.Tour> implements Tour {

    protected List<Step> stepList = new ArrayList<>();

    protected List<Consumer<ShowEvent>> tourShowListeners = null;
    protected List<Consumer<CancelEvent>> tourCancelListeners = null;
    protected List<Consumer<CompleteEvent>> tourCompleteListeners = null;
    protected List<Consumer<StartEvent>> tourStartListeners = null;
    protected List<Consumer<HideEvent>> tourHideListeners = null;

    protected org.vaadin.addons.producttour.tour.TourShowListener tourShowListener;
    protected org.vaadin.addons.producttour.tour.TourCancelListener tourCancelListener;
    protected org.vaadin.addons.producttour.tour.TourCompleteListener tourCompleteListener;
    protected org.vaadin.addons.producttour.tour.TourStartListener tourStartListener;
    protected org.vaadin.addons.producttour.tour.TourHideListener tourHideListener;

    /**
     * An extension of vaadin tour extending a certain target.
     */
    class CubaTour extends org.vaadin.addons.producttour.tour.Tour {

        public CubaTour(AbstractClientConnector target) {
            extendInternal(target);
        }

        @Override
        protected void extend(AbstractClientConnector target) {
        }

        /**
         * Extend a certain target.
         *
         * @param target The target to extend
         */
        protected void extendInternal(AbstractClientConnector target) {
            super.extend(target);

            target.addDetachListener(event -> cancel());
        }
    }

    /**
     * Construct a new tour.
     */
    public WebTour(Component component) {
        extension = createExtension(component);
    }

    /**
     * Create an extension for a vaadin tour.
     *
     * @param component The component to extend
     * @return The vaadin tour extension
     */
    protected org.vaadin.addons.producttour.tour.Tour createExtension(Component component) {
        return new CubaTour(component.unwrap(AbstractClientConnector.class));
    }

    /**
     * Initialize a tour extension.
     *
     * @param extension The tour extension
     */
    @Override
    protected void initExtension(org.vaadin.addons.producttour.tour.Tour extension) {
    }

    /**
     * Add the given step to the tour. Steps will be shown in the order they were added.
     *
     * @param step The step to be added
     */
    @Override
    public void addStep(Step step) {
        step.setTour(this);
        org.vaadin.addons.producttour.step.Step vaadinStep = step.unwrap(org.vaadin.addons.producttour.step.Step.class);
        extension.addStep(vaadinStep);
        stepList.add(step);
    }

    /**
     * Remove the given step from the tour.
     *
     * @param step The step to be removed
     */
    @Override
    public void removeStep(Step step) {
        org.vaadin.addons.producttour.step.Step vaadinStep = step.unwrap(org.vaadin.addons.producttour.step.Step.class);
        extension.removeStep(vaadinStep);
        stepList.remove(step);
    }

    /**
     * Get the last shown step.
     *
     * @return The last shown step or <code>null</code> if no step is shown
     */
    @Override
    public Step getCurrentStep() {
        org.vaadin.addons.producttour.step.Step currentStep = extension.getCurrentStep();
        return getStepByVaadinStep(currentStep);
    }

    /**
     * Trigger cancel on the current step, hiding it without advancing. The cancel provider for the
     * tour will be triggered.
     */
    @Override
    public void cancel() {
        extension.cancel();
    }

    /**
     * Show the first step and begin the tour. The start provider of the tour will be triggered.
     */
    @Override
    public void start() {
        extension.start();
    }

    /**
     * Hide the current step. The hide provider for the tour will be triggered.
     */
    @Override
    public void hide() {
        extension.hide();
    }

    /**
     * Show the next step in the order they were added.
     */
    @Override
    public void next() {
        extension.next();
    }

    /**
     * Show the previous step in the order they were added.
     */
    @Override
    public void back() {
        extension.back();
    }

    /**
     * Get the count of steps for this tour.
     *
     * @return The count of steps
     */
    @Override
    public int getStepCount() {
        return stepList.size();
    }

    /**
     * Get a step by its id.
     *
     * @param stepId The id of the step to get
     * @return The step with the given id or <code>null</code> if no step with the given id exists for
     * this tour
     */
    @Override
    @Nullable
    public Step getStepById(String stepId) {
        return stepList.stream()
                .filter(s -> Objects.equals(s.getId(), stepId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get a step by its index.
     *
     * @param index The index of the step to get
     * @return The step at the given index
     */
    @Override
    public Step getStepByIndex(int index) {
        return stepList.get(index);
    }

    /**
     * Get the steps of the tour.
     *
     * @return The steps of the tour inside an unmodifiable container
     */
    @Override
    public List<Step> getSteps() {
        return Collections.unmodifiableList(stepList);
    }

    /**
     * Get a cuba step by its vaadin step.
     *
     * @param vaadinStep The vaadin step
     * @return The cuba step by the vaadin step
     */
    @Nullable
    protected Step getStepByVaadinStep(org.vaadin.addons.producttour.step.Step vaadinStep) {
        for (Step step : getSteps()) {
            org.vaadin.addons.producttour.step.Step internalVaadinStep = step.unwrap(org.vaadin.addons.producttour.step.Step.class);
            if (internalVaadinStep == vaadinStep) {
                return step;
            }
        }
        return null;
    }

    /**
     * Add the given listener to the tour that will be triggered if the tour is shown.
     *
     * @param showListener The listener to be added
     */
    @Override
    public void addShowListener(Consumer<ShowEvent> showListener) {
        if (tourShowListeners == null) {
            tourShowListeners = new ArrayList<>();

            this.tourShowListener = (org.vaadin.addons.producttour.tour.TourShowListener) event -> {
                org.vaadin.addons.producttour.step.Step currentStep = event.getCurrentStep();
                org.vaadin.addons.producttour.step.Step previousStep = event.getPreviousStep();
                ShowEvent e = new ShowEvent(WebTour.this, getStepByVaadinStep(previousStep), getStepByVaadinStep(currentStep));
                for (Consumer<ShowEvent> tourShowListener : tourShowListeners) {
                    tourShowListener.accept(e);
                }
            };

            extension.addShowListener(this.tourShowListener);

        }
        if (!tourShowListeners.contains(showListener)) {
            tourShowListeners.add(showListener);
        }
    }

    /**
     * Remove the given listener from the tour.
     *
     * @param showListener The listener to be removed.
     */
    @Override
    public void removeShowListener(Consumer<ShowEvent> showListener) {
        if (tourShowListeners != null) {
            tourShowListeners.remove(showListener);

            if (tourShowListeners.isEmpty()) {
                tourShowListeners = null;
                extension.removeShowListener(this.tourShowListener);
                this.tourShowListener = null;
            }
        }
    }

    /**
     * Add the given listener to the tour that will be triggered if the tour is cancelled.
     *
     * @param cancelListener The listener to be added
     */
    @Override
    public void addCancelListener(Consumer<CancelEvent> cancelListener) {
        if (tourCancelListeners == null) {
            tourCancelListeners = new ArrayList<>();

            this.tourCancelListener = (org.vaadin.addons.producttour.tour.TourCancelListener) event -> {
                CancelEvent e = new CancelEvent(WebTour.this);
                for (Consumer<CancelEvent> tourCancelListener : tourCancelListeners) {
                    tourCancelListener.accept(e);
                }
            };

            extension.addCancelListener(this.tourCancelListener);

        }
        if (!tourCancelListeners.contains(cancelListener)) {
            tourCancelListeners.add(cancelListener);
        }
    }

    /**
     * Remove the given listener from the tour.
     *
     * @param cancelListener The listener to be removed.
     */
    @Override
    public void removeCancelListener(Consumer<CancelEvent> cancelListener) {
        if (tourCancelListeners != null) {
            tourCancelListeners.remove(cancelListener);

            if (tourCancelListeners.isEmpty()) {
                tourCancelListeners = null;
                extension.removeCancelListener(this.tourCancelListener);
                this.tourCancelListener = null;
            }
        }
    }

    /**
     * Add the given listener to the tour that will be triggered if the tour is completed.
     *
     * @param completeListener The listener to be added
     */
    @Override
    public void addCompleteListener(Consumer<CompleteEvent> completeListener) {
        if (tourCompleteListeners == null) {
            tourCompleteListeners = new ArrayList<>();

            this.tourCompleteListener = (org.vaadin.addons.producttour.tour.TourCompleteListener) event -> {
                CompleteEvent e = new CompleteEvent(WebTour.this);
                for (Consumer<CompleteEvent> tourCompleteListener : tourCompleteListeners) {
                    tourCompleteListener.accept(e);
                }
            };

            extension.addCompleteListener(this.tourCompleteListener);

        }
        if (!tourCompleteListeners.contains(completeListener)) {
            tourCompleteListeners.add(completeListener);
        }
    }

    /**
     * Remove the given listener from the tour.
     *
     * @param completeListener The listener to be removed.
     */
    @Override
    public void removeCompleteListener(Consumer<CompleteEvent> completeListener) {
        if (tourCompleteListeners != null) {
            tourCompleteListeners.remove(completeListener);

            if (tourCompleteListeners.isEmpty()) {
                tourCompleteListeners = null;
                extension.removeCompleteListener(this.tourCompleteListener);
                this.tourCompleteListener = null;
            }
        }
    }

    /**
     * Add the given listener to the tour that will be triggered if the tour is hidden.
     *
     * @param hideListener The listener to be added
     */
    @Override
    public void addHideListener(Consumer<HideEvent> hideListener) {
        if (tourHideListeners == null) {
            tourHideListeners = new ArrayList<>();

            this.tourHideListener = (org.vaadin.addons.producttour.tour.TourHideListener) event -> {
                HideEvent e = new HideEvent(WebTour.this);
                for (Consumer<HideEvent> tourHideListener : tourHideListeners) {
                    tourHideListener.accept(e);
                }
            };

            extension.addHideListener(this.tourHideListener);

        }
        if (!tourHideListeners.contains(hideListener)) {
            tourHideListeners.add(hideListener);
        }
    }

    /**
     * Remove the given listener from the tour.
     *
     * @param hideListener The listener to be removed.
     */
    @Override
    public void removeHideListener(Consumer<HideEvent> hideListener) {
        if (tourHideListeners != null) {
            tourHideListeners.remove(hideListener);

            if (tourHideListeners.isEmpty()) {
                tourHideListeners = null;
                extension.removeHideListener(this.tourHideListener);
                this.tourHideListener = null;
            }
        }
    }

    /**
     * Add the given listener to the tour that will be triggered if the tour is started.
     *
     * @param startListener The listener to be added
     */
    @Override
    public void addStartListener(Consumer<StartEvent> startListener) {
        if (tourStartListeners == null) {
            tourStartListeners = new ArrayList<>();

            this.tourStartListener = (org.vaadin.addons.producttour.tour.TourStartListener) event -> {
                StartEvent e = new StartEvent(WebTour.this);
                for (Consumer<StartEvent> tourStartListener : tourStartListeners) {
                    tourStartListener.accept(e);
                }
            };

            extension.addStartListener(this.tourStartListener);

        }
        if (!tourStartListeners.contains(startListener)) {
            tourStartListeners.add(startListener);
        }
    }

    /**
     * Remove the given listener from the tour.
     *
     * @param startListener The listener to be removed.
     */
    @Override
    public void removeStartListener(Consumer<StartEvent> startListener) {
        if (tourStartListeners != null) {
            tourStartListeners.remove(startListener);

            if (tourStartListeners.isEmpty()) {
                tourStartListeners = null;
                extension.removeStartListener(this.tourStartListener);
                this.tourStartListener = null;
            }
        }
    }
}