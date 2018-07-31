package com.company.scenarioaddon.web.gui.components;

import com.haulmont.cuba.gui.components.AbstractAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import org.vaadin.addons.producttour.tour.TourShowListener;

import java.util.*;

public class WebTour implements Tour {


    protected org.vaadin.addons.producttour.tour.Tour tour;

    protected HashMap<org.vaadin.addons.producttour.step.Step, WebStep> stepMap = new HashMap<>();

    protected List<TourShowListener> tourShowListeners = null;
    protected List<TourCancelListener> tourCancelListeners = null;
    protected List<TourCompleteListener> tourCompleteListeners = null;
    protected List<TourStartListener> tourStartListeners = null;
    protected List<TourHideListener> tourHideListeners = null;

    protected org.vaadin.addons.producttour.tour.TourShowListener tourShowListener;
    protected org.vaadin.addons.producttour.tour.TourCancelListener tourCancelListener;
    protected org.vaadin.addons.producttour.tour.TourCompleteListener tourCompleteListener;
    protected org.vaadin.addons.producttour.tour.TourStartListener tourStartListener;
    protected org.vaadin.addons.producttour.tour.TourHideListener tourHideListener;

    public WebTour() {
        tour = new org.vaadin.addons.producttour.tour.Tour();
    }

    public org.vaadin.addons.producttour.tour.Tour getTour() {
        return tour;
    }

    @Override
    public void addStep(Step step) {
        WebStep webStep = (WebStep) step;
        step.setTour(this);
        tour.addStep(webStep.getStep());
        stepMap.put(webStep.getStep(), webStep);
    }

    @Override
    public void removeStep(Step step) {
        WebStep webStep = (WebStep) step;
        tour.removeStep(webStep.getStep());
        stepMap.remove(webStep.getStep());
    }

    @Override
    public Step getCurrentStep() {
        org.vaadin.addons.producttour.step.Step currentStep = tour.getCurrentStep();
        return stepMap.get(currentStep);
    }

    @Override
    public void cancel() {
        tour.cancel();
    }

    @Override
    public void start() {
        tour.start();
    }

    @Override
    public void hide() {
        tour.hide();
    }

    @Override
    public void next() {
        tour.next();
    }

    @Override
    public void back() {
        tour.back();
    }

    @Override
    public int getStepCount() {
        return stepMap.size();
    }

    @Override
    public Step getStepById(String stepId) {
        Set<org.vaadin.addons.producttour.step.Step> steps = stepMap.keySet();
        org.vaadin.addons.producttour.step.Step step = steps.stream()
                .filter(s -> Objects.equals(s.getId(), stepId))
                .findFirst()
                .orElse(null);
        return stepMap.get(step);
    }

    @Override
    public List<Step> getSteps() {
        return new ArrayList<>(stepMap.values());
    }

    @Override
    public void addShowListener(TourShowListener listener) {
        if (tourShowListeners == null) {
            tourShowListeners = new ArrayList<>();

            this.tourShowListener = (org.vaadin.addons.producttour.tour.TourShowListener) event -> {
                org.vaadin.addons.producttour.step.Step currentStep = event.getCurrentStep();
                org.vaadin.addons.producttour.step.Step previousStep = event.getPreviousStep();
                TourShowListener.ShowEvent e = new TourShowListener.ShowEvent(WebTour.this, stepMap.get(currentStep), stepMap.get(previousStep));
                for (TourShowListener tourShowListener: tourShowListeners) {
                    tourShowListener.onShow(e);
                }
            };

            tour.addShowListener(this.tourShowListener);

        }
        if (!tourShowListeners.contains(listener)) {
            tourShowListeners.add(listener);
        }
    }

    @Override
    public void removeShowListener(TourShowListener listener) {
        tourShowListeners.remove(listener);

        if (tourShowListeners.isEmpty()) {
            tourShowListeners = null;
            tour.removeShowListener(this.tourShowListener);
        }
    }

    @Override
    public void addCancelListener(TourCancelListener listener) {
        if (tourCancelListeners == null) {
            tourCancelListeners = new ArrayList<>();

            this.tourCancelListener = (org.vaadin.addons.producttour.tour.TourCancelListener) event -> {
                TourCancelListener.CancelEvent e = new TourCancelListener.CancelEvent(WebTour.this);
                for (TourCancelListener tourCancelListener: tourCancelListeners) {
                    tourCancelListener.onCancel(e);
                }
            };

            tour.addCancelListener(this.tourCancelListener);

        }
        if (!tourCancelListeners.contains(listener)) {
            tourCancelListeners.add(listener);
        }
    }

    @Override
    public void removeCancelListener(TourCancelListener listener) {
        tourCancelListeners.remove(listener);

        if (tourCancelListeners.isEmpty()) {
            tourCancelListeners = null;
            tour.removeCancelListener(this.tourCancelListener);
        }
    }

    @Override
    public void addCompleteListener(TourCompleteListener listener) {
        if (tourCompleteListeners == null) {
            tourCompleteListeners = new ArrayList<>();

            this.tourCompleteListener = (org.vaadin.addons.producttour.tour.TourCompleteListener) event -> {
                TourCompleteListener.CompleteEvent e = new TourCompleteListener.CompleteEvent(WebTour.this);
                for (TourCompleteListener tourCompleteListener: tourCompleteListeners) {
                    tourCompleteListener.onComplete(e);
                }
            };

            tour.addCompleteListener(this.tourCompleteListener);

        }
        if (!tourCompleteListeners.contains(listener)) {
            tourCompleteListeners.add(listener);
        }
    }

    @Override
    public void removeCompleteListener(TourCompleteListener listener) {
        tourCompleteListeners.remove(listener);

        if (tourCompleteListeners.isEmpty()) {
            tourCompleteListeners = null;
            tour.removeCompleteListener(this.tourCompleteListener);
        }
    }

    @Override
    public void addHideListener(TourHideListener listener) {
        if (tourHideListeners == null) {
            tourHideListeners = new ArrayList<>();

            this.tourHideListener = (org.vaadin.addons.producttour.tour.TourHideListener) event -> {
                TourHideListener.HideEvent e = new TourHideListener.HideEvent(WebTour.this);
                for (TourHideListener tourHideListener: tourHideListeners) {
                    tourHideListener.onHide(e);
                }
            };

            tour.addHideListener(this.tourHideListener);

        }
        if (!tourHideListeners.contains(listener)) {
            tourHideListeners.add(listener);
        }
    }

    @Override
    public void removeHideListener(TourHideListener listener) {
        tourHideListeners.remove(listener);

        if (tourHideListeners.isEmpty()) {
            tourHideListeners = null;
            tour.removeHideListener(this.tourHideListener);
        }
    }

    @Override
    public void addStartListener(TourStartListener listener) {
        if (tourStartListeners == null) {
            tourStartListeners = new ArrayList<>();

            this.tourStartListener = (org.vaadin.addons.producttour.tour.TourStartListener) event -> {
                TourStartListener.StartEvent e = new TourStartListener.StartEvent(WebTour.this);
                for (TourStartListener tourStartListener: tourStartListeners) {
                    tourStartListener.onStart(e);
                }
            };

            tour.addStartListener(this.tourStartListener);

        }
        if (!tourStartListeners.contains(listener)) {
            tourStartListeners.add(listener);
        }
    }

    @Override
    public void removeStartListener(TourStartListener listener) {
        tourStartListeners.remove(listener);

        if (tourStartListeners.isEmpty()) {
            tourStartListeners = null;
            tour.removeStartListener(this.tourStartListener);
        }
    }

    public Action startAction() {
        return new BaseAction("startAction") {
            @Override
            public void actionPerform(Component component) {
                if (getCurrentStep() != null) {
                    cancel();
                }
                start();
            }
        };
    }


}