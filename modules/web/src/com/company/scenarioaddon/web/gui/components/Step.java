/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import com.haulmont.cuba.gui.components.Component;

import java.util.EventObject;
import java.util.List;
import java.util.function.Consumer;

public interface Step {

    <X> X getStep();

    void setTour(Tour tour);
    Tour getTour();

    void setSizeUndefined();

    void setSizeFull();

    void setTitle(String title);
    String getTitle();

    void setText(String text);
    String getText();

    boolean isVisible();

    String getId();

    Component getAttachedTo();

    void setAttachedTo(Component component);

    void setDetached();

    void setCancellable(boolean cancellable);
    boolean isCancellable();

    void setModal(boolean modal);
    boolean isModal();

    void setScrollTo(boolean scrollTo);
    boolean isScrollTo();

    void setTextContentMode(ContentMode contentMode);
    ContentMode getTextContentMode();

    void setTitleContentMode(ContentMode contentMode);
    ContentMode getTitleContentMode();

    void setAnchor(StepAnchor anchor);
    StepAnchor getAnchor();

    List<StepButton> getButtons();

    StepButton getButtonByIndex(int index);

    int getButtonCount();

    void addButton(StepButton button);

    void removeButton(StepButton button);

    void cancel();

    void complete();

    void hide();

    void show();

    void scrollTo();

    enum StepAnchor {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT
    }

    enum ContentMode {
        TEXT,
        PREFORMATTED,
        HTML
    }

    void addCancelListener(Consumer<CancelEvent> consumer);

    void removeCancelListener(Consumer<CancelEvent> consumer);

    class CancelEvent extends StepEvent {

        public CancelEvent(Step source) { super(source); }

    }

    void addCompleteListener(Consumer<CompleteEvent> consumer);

    void removeCompleteListener(Consumer<CompleteEvent> consumer);

    class CompleteEvent extends StepEvent{

        public CompleteEvent(Step source) {
            super(source);
        }
    }


    void addHideListener(Consumer<HideEvent> consumer);

    void removeHideListener(Consumer<HideEvent> consumer);

    class HideEvent extends StepEvent {

        public HideEvent(Step source) {
            super(source);
        }
    }

    void addShowListener(Consumer<ShowEvent> consumer);

    void removeShowListener(Consumer<ShowEvent> consumer);

    class ShowEvent extends StepEvent {

        public ShowEvent(Step source) {
            super(source);
        }
    }

    class StepEvent extends EventObject implements TourProvider, StepProvider {

        public StepEvent(Object source) {
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
