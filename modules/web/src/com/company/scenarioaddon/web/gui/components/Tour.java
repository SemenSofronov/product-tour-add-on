package com.company.scenarioaddon.web.gui.components;

import java.util.List;

public interface Tour {

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

    List<Step> getSteps();

    void addShowListener(TourShowListener listener);

    void removeShowListener(TourShowListener listener);

    interface TourShowListener {
        void onShow(ShowEvent event);

        class ShowEvent extends TourEvent {

            private final Step previousStep;
            private final Step currentStep;

            public ShowEvent(Tour source, Step previousStep, Step currentStep) {
                super(source);
                this.previousStep = previousStep;
                this.currentStep = currentStep;
            }

            public Step getPreviousStep() {
                return previousStep;
            }

            public Step getCurrentStep() {
                return currentStep;
            }
        }
    }

    void addCancelListener(TourCancelListener listener);

    void removeCancelListener(TourCancelListener listener);

    interface TourCancelListener {
        void onCancel(CancelEvent event);

        class CancelEvent extends TourEvent {

            public CancelEvent(Tour source) {
                super(source);
            }
        }
    }

    void addCompleteListener(TourCompleteListener listener);

    void removeCompleteListener(TourCompleteListener listener);

    interface TourCompleteListener {
        void onComplete(CompleteEvent event);

        class CompleteEvent extends TourEvent {

            public CompleteEvent(Tour source) {
                super(source);
            }
        }
    }

    void addHideListener(TourHideListener listener);

    void removeHideListener(TourHideListener listener);

    interface TourHideListener {
        void onHide(HideEvent event);

        class HideEvent extends TourEvent {

            public HideEvent(Tour source) {
                super(source);
            }
        }
    }

    void addStartListener(TourStartListener listener);

    void removeStartListener(TourStartListener listener);

    interface TourStartListener {
        void onStart(StartEvent event);

        class StartEvent extends TourEvent {

            public StartEvent(Tour source) {
                super(source);
            }
        }
    }
}