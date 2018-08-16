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

    <X> X unwrap(Class<X> internalClass);

    void setTour(Tour tour);
    Tour getTour();

    void setSizeUndefined();

    void setSizeFull();

    void setTitle(String title);
    String getTitle();

    void setText(String text);
    String getText();

    boolean isVisible();

    void setWidth(String width);
    float getWidth();

    void setHeight(String height);
    float getHeight();

    int getHeightUnits();

    int getWidthUnits();

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

    void addCancelListener(Consumer<CancelEvent> listener);

    void removeCancelListener(Consumer<CancelEvent> listener);

    class CancelEvent extends StepEvent {

        public CancelEvent(Step source) { super(source); }

    }

    void addCompleteListener(Consumer<CompleteEvent> listener);

    void removeCompleteListener(Consumer<CompleteEvent> listener);

    class CompleteEvent extends StepEvent{

        public CompleteEvent(Step source) {
            super(source);
        }
    }


    void addHideListener(Consumer<HideEvent> listener);

    void removeHideListener(Consumer<HideEvent> listener);

    class HideEvent extends StepEvent {

        public HideEvent(Step source) {
            super(source);
        }
    }

    void addShowListener(Consumer<ShowEvent> listener);

    void removeShowListener(Consumer<ShowEvent> listener);

    class ShowEvent extends StepEvent {

        public ShowEvent(Step source) {
            super(source);
        }
    }

    class StepEvent extends EventObject implements TourProvider, StepProvider {

        public StepEvent(Step source) {
            super(source);
        }

        @Override
        public Step getSource() {
            return (Step) source;
        }

        @Override
        public Step getStep() {
            return getSource();
        }

        @Override
        public Tour getTour() {
            Step step = getStep();
            return step != null ? step.getTour() : null;
        }
    }

}
