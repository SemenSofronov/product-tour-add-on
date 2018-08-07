package com.company.scenarioaddon.web.gui.components;

import java.util.EventObject;
import java.util.List;
import java.util.function.Consumer;

public interface Tour {

    <X> X unwrap(Class<X> internalClass);

    void addStep(Step step);

    void removeStep(Step step);

    Step getCurrentStep();

    void cancel();

    void start();

    void hide();

    void next();

    void back();

    int getStepCount();

    Step getStepById(String stepId);

    Step getStepByIndex(int index);

    List<Step> getSteps();

    void addShowListener(Consumer<ShowEvent>  listener);

    void removeShowListener(Consumer<ShowEvent>  listener);

    class ShowEvent extends TourEvent {

        private final Step previousStep;
        private final Step currentStep;

        public ShowEvent(Tour source, Step previousStep, Step currentStep) {
            super(source);
            this.previousStep = previousStep;
            this.currentStep = currentStep;
        }
    }

    void addCancelListener(Consumer<CancelEvent> listener);

    void removeCancelListener(Consumer<CancelEvent>  listener);

    class CancelEvent extends TourEvent {

        public CancelEvent(Tour source) {
            super(source);
        }
    }

    void addCompleteListener(Consumer<CompleteEvent> listener);

    void removeCompleteListener(Consumer<CompleteEvent>  listener);

    class CompleteEvent extends TourEvent {

        public CompleteEvent(Tour source) {
            super(source);
        }
    }

    void addHideListener(Consumer<HideEvent>  listener);

    void removeHideListener(Consumer<HideEvent>  listener);

    class HideEvent extends TourEvent {

        public HideEvent(Tour source) {
            super(source);
        }
    }

    void addStartListener(Consumer<StartEvent>  listener);

    void removeStartListener(Consumer<StartEvent>  listener);

    class StartEvent extends TourEvent {

        public StartEvent(Tour source) {
            super(source);
        }
    }

    class TourEvent extends EventObject implements TourProvider {

        public TourEvent(Object source) {
            super(source);
        }

        @Override
        public Tour getTour() {
            return (Tour) getSource();
        }
    }
}