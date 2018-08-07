package com.company.scenarioaddon.web.gui.components;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class WebTour implements Tour {
    protected org.vaadin.addons.producttour.tour.Tour extension;

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

    public WebTour() {
        extension = createExtension();
    }

    protected org.vaadin.addons.producttour.tour.Tour createExtension() {
        return new org.vaadin.addons.producttour.tour.Tour();
    }

    @Override
    public <X> X unwrap(Class<X> internalClass) {
        return internalClass.cast(extension);
    }

    @Override
    public void addStep(Step step) {
        step.setTour(this);
        org.vaadin.addons.producttour.step.Step vaadinStep = step.unwrap(org.vaadin.addons.producttour.step.Step.class);
        extension.addStep(vaadinStep);
        stepList.add(step);
    }

    @Override
    public void removeStep(Step step) {
        org.vaadin.addons.producttour.step.Step vaadinStep = step.unwrap(org.vaadin.addons.producttour.step.Step.class);
        extension.removeStep(vaadinStep);
        stepList.remove(step);
    }

    @Override
    public Step getCurrentStep() {
        org.vaadin.addons.producttour.step.Step currentStep = extension.getCurrentStep();
        return getStepByVaadinStep(currentStep);
    }

    @Override
    public void cancel() {
        extension.cancel();
    }

    @Override
    public void start() {
        extension.start();
    }

    @Override
    public void hide() {
        extension.hide();
    }

    @Override
    public void next() {
        extension.next();
    }

    @Override
    public void back() {
        extension.back();
    }

    @Override
    public int getStepCount() {
        return stepList.size();
    }

    @Override
    @Nullable
    public Step getStepById(String stepId) {
        return stepList.stream()
                .filter(s -> Objects.equals(s.getId(), stepId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Step getStepByIndex(int index) {
        return stepList.get(index);
    }

    @Override
    public List<Step> getSteps() {
        return Collections.unmodifiableList(stepList);
    }

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

    @Override
    public void addShowListener(Consumer<ShowEvent> listener) {
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
        if (!tourShowListeners.contains(listener)) {
            tourShowListeners.add(listener);
        }
    }

    @Override
    public void removeShowListener(Consumer<ShowEvent> listener) {
        if (tourShowListeners != null) {
            tourShowListeners.remove(listener);

            if (tourShowListeners.isEmpty()) {
                tourShowListeners = null;
                extension.removeShowListener(this.tourShowListener);
                this.tourShowListener = null;
            }
        }
    }

    @Override
    public void addCancelListener(Consumer<CancelEvent> listener) {
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
        if (!tourCancelListeners.contains(listener)) {
            tourCancelListeners.add(listener);
        }
    }

    @Override
    public void removeCancelListener(Consumer<CancelEvent> listener) {
        if (tourCancelListeners != null) {
            tourCancelListeners.remove(listener);

            if (tourCancelListeners.isEmpty()) {
                tourCancelListeners = null;
                extension.removeCancelListener(this.tourCancelListener);
                this.tourCancelListener = null;
            }
        }
    }

    @Override
    public void addCompleteListener(Consumer<CompleteEvent> listener) {
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
        if (!tourCompleteListeners.contains(listener)) {
            tourCompleteListeners.add(listener);
        }
    }

    @Override
    public void removeCompleteListener(Consumer<CompleteEvent> listener) {
        if (tourCompleteListeners != null) {
            tourCompleteListeners.remove(listener);

            if (tourCompleteListeners.isEmpty()) {
                tourCompleteListeners = null;
                extension.removeCompleteListener(this.tourCompleteListener);
                this.tourCompleteListener = null;
            }
        }
    }

    @Override
    public void addHideListener(Consumer<HideEvent> listener) {
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
        if (!tourHideListeners.contains(listener)) {
            tourHideListeners.add(listener);
        }
    }

    @Override
    public void removeHideListener(Consumer<HideEvent> listener) {
        if (tourHideListeners != null) {
            tourHideListeners.remove(listener);

            if (tourHideListeners.isEmpty()) {
                tourHideListeners = null;
                extension.removeHideListener(this.tourHideListener);
                this.tourHideListener = null;
            }
        }
    }

    @Override
    public void addStartListener(Consumer<StartEvent> listener) {
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
        if (!tourStartListeners.contains(listener)) {
            tourStartListeners.add(listener);
        }
    }

    @Override
    public void removeStartListener(Consumer<StartEvent> listener) {
        if (tourStartListeners != null) {
            tourStartListeners.remove(listener);

            if (tourStartListeners.isEmpty()) {
                tourStartListeners = null;
                extension.removeStartListener(this.tourStartListener);
                this.tourStartListener = null;
            }
        }
    }
}