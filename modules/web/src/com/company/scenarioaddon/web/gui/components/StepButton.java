package com.company.scenarioaddon.web.gui.components;

import com.haulmont.cuba.gui.components.Component;

public interface StepButton {

    void addStepButtonClickListener(StepButtonClickListener stepButtonClickListener);

    void removeStepButtonClickListener(StepButtonClickListener stepButtonClickListener);

    Step getStep();

    void setStep(Step step);

    String getCaption();

    void setCaption(String caption);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void addStyleName(String style);

    void removeStyleName(String style);

    String getStyleName();

    void setStyleName(String style);


    interface StepButtonClickListener {
        void onClick(ClickEvent event);

        abstract class ClickEvent implements TourProvider {
            private final Component.MouseEventDetails details;

            protected StepButton source;

            public ClickEvent(StepButton source) {
                this(source, null);
            }

            public ClickEvent(StepButton source, Component.MouseEventDetails details) {
                this.source = source;
                this.details = details;
            }

            public StepButton getSource() {
                return source;
            }

            public int getClientX() {
                if (null != details) {
                    return details.getClientX();
                } else {
                    return -1;
                }
            }

            public int getClientY() {
                if (null != details) {
                    return details.getClientY();
                } else {
                    return -1;
                }
            }

            public int getRelativeX() {
                if (null != details) {
                    return details.getRelativeX();
                } else {
                    return -1;
                }
            }

            public int getRelativeY() {
                if (null != details) {
                    return details.getRelativeY();
                } else {
                    return -1;
                }
            }

            public boolean isAltKey() {
                if (null != details) {
                    return details.isAltKey();
                } else {
                    return false;
                }
            }

            public boolean isCtrlKey() {
                if (null != details) {
                    return details.isCtrlKey();
                } else {
                    return false;
                }
            }

            public boolean isMetaKey() {
                if (null != details) {
                    return details.isMetaKey();
                } else {
                    return false;
                }
            }

            public boolean isShiftKey() {
                if (null != details) {
                    return details.isShiftKey();
                } else {
                    return false;
                }
            }
        }
    }
}
