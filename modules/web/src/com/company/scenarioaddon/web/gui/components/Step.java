/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.components;

import com.haulmont.cuba.gui.components.Component;

import java.util.List;

public interface Step {

    void setTour(Tour tour);

    void setTitle(String title);
    String getTitle();

    void setText(String text);
    String getText();

    void setSizeFull();

    boolean isVisible();

    List<WebStepButton> getButtons();

    WebStepButton getButtonByIndex(int index);

    int getButtonCount();

    String getId();

    Component getAttachedTo();

    void setAttachedTo(Component component);

    void setDetached();

    void setSizeUndefined();

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

    void cancel();

    void complete();

    void hide();

    void show();

    void scrollTo();

    Tour getTour();

    void addButton(StepButton button);

    void removeButton(StepButton button);

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

    void addCancelListener(StepCancelListener listener);

    void removeCancelListener(StepCancelListener listener);

    interface StepCancelListener {

        void onCancel(CancelEvent event);

        class CancelEvent extends StepEvent {

            public CancelEvent(Step source) { super(source); }

        }
    }

    void addCompleteListener(StepCompleteListener listener);

    void removeCompleteListener(StepCompleteListener listener);

    interface StepCompleteListener {
        void onComplete(CompleteEvent event);

        class CompleteEvent extends StepEvent{

            public CompleteEvent(Step source) {
                super(source);
            }
        }
    }


    void addHideListener(StepHideListener listener);

    void removeHideListener(StepHideListener listener);

    interface StepHideListener {
        void onHide(HideEvent event);

        class HideEvent extends StepEvent {

            public HideEvent(Step source) {
                super(source);
            }
        }
    }

    void addShowListener(StepShowListener listener);

    void removeShowListener(StepShowListener listener);

    interface StepShowListener {
        void onShow(ShowEvent event);

        class ShowEvent extends StepEvent {

            public ShowEvent(Step source) {
                super(source);
            }
        }
    }

}
