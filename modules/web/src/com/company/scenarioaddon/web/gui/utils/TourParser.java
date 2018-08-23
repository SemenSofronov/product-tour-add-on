/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.utils;

import com.company.scenarioaddon.web.gui.components.*;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.haulmont.bali.util.Preconditions;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.ComponentsHelper;
import com.haulmont.cuba.gui.components.AbstractWindow;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Parser for {@link WebTour} objects
 */
@Component("scenarioaddon_TourParser")
public class TourParser {

    protected static Gson gson = new Gson();

    protected Messages messages;

    protected String messagesPack;

    protected AbstractWindow extension;

    /**
     * Creates a tour from a given json extending a given window using a given messages pack.
     *
     * @param json         the json file
     * @param messagesPack the messages pack
     * @param extensionFor the window to extend
     * @return the tour
     */
    public Tour createTour(String json, String messagesPack, AbstractWindow extensionFor) {

        Preconditions.checkNotNullArgument(extensionFor, "Extension is required!");

        initTourParser(messagesPack, extensionFor);

        Tour tour = new WebTour(extensionFor);
        loadTour(json, tour);
        return tour;
    }

    /**
     * Initialize the tour with given messages pack and window.
     *
     * @param messagesPack the messages pack
     * @param extensionFor the window
     */
    protected void initTourParser(String messagesPack, AbstractWindow extensionFor) {
        this.messagesPack = messagesPack;
        extension = extensionFor;
        messages = AppBeans.get(Messages.class);
    }

    /**
     * Load tour from the json file.
     *
     * @param json the json file
     * @param tour the loading tour
     */
    protected void loadTour(String json, Tour tour) {
        ArrayList steps = gson.fromJson(json, ArrayList.class);
        for (Object step : steps) {
            LinkedTreeMap stepMap = (LinkedTreeMap) step;
            loadStep(stepMap, tour);
        }
    }

    /**
     * Load step from a step map for a tour.
     *
     * @param stepMap the step map
     * @param tour    the tour
     */
    protected void loadStep(LinkedTreeMap stepMap, Tour tour) {
        Step webStep = createStepWithId(stepMap);

        loadText(stepMap, webStep);
        loadTitle(stepMap, webStep);
        loadTextContentMode(stepMap, webStep);
        loadTitleContentMode(stepMap, webStep);

        loadWidth(stepMap, webStep);
        loadHeight(stepMap, webStep);

        loadModal(stepMap, webStep);
        loadCancellable(stepMap, webStep);
        loadScrollTo(stepMap, webStep);

        loadAttachTo(stepMap, webStep);
        loadStepAnchor(stepMap, webStep);

        loadButtons(stepMap, webStep);

        tour.addStep(webStep);
    }

    /**
     * Load step buttons from a step map for a step.
     *
     * @param stepMap the step map
     * @param webStep the step
     */
    protected void loadButtons(LinkedTreeMap stepMap, Step webStep) {
        if (stepMap.get("buttons") != null) {
            ArrayList buttons = (ArrayList) stepMap.get("buttons");

            for (Object button : buttons) {
                LinkedTreeMap buttonMap = (LinkedTreeMap) button;

                loadButton(buttonMap, webStep);
            }
        }
    }

    /**
     * Load step button from a button map for a step.
     *
     * @param buttonMap the button map
     * @param webStep   the step
     */
    protected void loadButton(LinkedTreeMap buttonMap, Step webStep) {
        StepButton stepButton = createStepButtonWithCaption(buttonMap);

        loadStyle(buttonMap, stepButton);
        loadEnabled(buttonMap, stepButton);
        loadClickListener(buttonMap, stepButton);

        webStep.addButton(stepButton);
    }

    /**
     * Creates a step button with a caption loaded from a button map or with default value.
     *
     * @param buttonMap the button map
     * @return the step button
     */
    protected StepButton createStepButtonWithCaption(LinkedTreeMap buttonMap) {
        if (buttonMap.get("caption") != null) {
            String caption = (String) buttonMap.get("caption");

            caption = loadResourceString(caption);
            return new WebStepButton(caption);
        }
        return new WebStepButton("Test");
    }

    /**
     * Load a click listener from a button map for a step button.
     *
     * @param buttonMap  the button map
     * @param stepButton the step button
     */
    protected void loadClickListener(LinkedTreeMap buttonMap, StepButton stepButton) {
        if (buttonMap.get("action") != null) {
            String action = (String) buttonMap.get("action");

            Consumer<StepButton.ClickEvent> clickListener = getClickListener(action);
            if (clickListener != null) {
                stepButton.addStepButtonClickListener(clickListener);
            }
        }
    }

    /**
     * Load an enabled value from a button map for a step button.
     *
     * @param buttonMap  the button map
     * @param stepButton the step button
     */
    protected void loadEnabled(LinkedTreeMap buttonMap, StepButton stepButton) {
        if (buttonMap.get("enabled") != null) {
            String enabled = (String) buttonMap.get("enabled");

            stepButton.setEnabled(Boolean.valueOf(enabled));
        }
    }

    /**
     * Load a style value from a button map for a step button.
     *
     * @param buttonMap  the button map
     * @param stepButton the step button
     */
    protected void loadStyle(LinkedTreeMap buttonMap, StepButton stepButton) {
        if (buttonMap.get("style") != null) {
            String style = (String) buttonMap.get("style");

            stepButton.setStyleName(style);
        }
    }

    /**
     * Creates a step with an id loaded from a step map or with random value.
     *
     * @param stepMap the step map
     * @return the step
     */
    protected Step createStepWithId(LinkedTreeMap stepMap) {
        String id;
        if (stepMap.get("id") != null) {
            id = (String) stepMap.get("id");
            return new WebStep(id);
        }
        id = UUID.randomUUID().toString();
        return new WebStep(id);

    }

    /**
     * Load an attachTo value from a step map for a step.
     *
     * @param stepMap the step map
     * @param webStep the step
     */
    protected void loadAttachTo(LinkedTreeMap stepMap, Step webStep) {
        if (stepMap.get("attachTo") != null) {
            String attachTo = (String) stepMap.get("attachTo");
            com.haulmont.cuba.gui.components.Component cubaComponent = getCubaComponent(attachTo);

            if (cubaComponent != null) {
                webStep.setAttachedTo(cubaComponent);
            }
        }
    }

    /**
     * Load a step anchor value from a step map for a step.
     *
     * @param stepMap the step map
     * @param webStep the step
     */
    protected void loadStepAnchor(LinkedTreeMap stepMap, Step webStep) {
        if (stepMap.get("anchor") != null) {
            String anchor = (String) stepMap.get("anchor");
            Step.StepAnchor stepAnchor = Step.StepAnchor.fromId(anchor);

            if (stepAnchor == null) {
                webStep.setAnchor(Step.StepAnchor.RIGHT);
                return;
            }

            webStep.setAnchor(stepAnchor);
        }
    }

    /**
     * Load a title content mode value from a step map for a step.
     *
     * @param stepMap the step map
     * @param webStep the step
     */
    protected void loadTitleContentMode(LinkedTreeMap stepMap, Step webStep) {
        if (stepMap.get("titleContentMode") != null) {
            String titleContentMode = (String) stepMap.get("titleContentMode");
            Step.ContentMode contentMode = Step.ContentMode.fromId(titleContentMode);

            if (contentMode == null) {
                webStep.setTitleContentMode(Step.ContentMode.TEXT);
                return;
            }

            webStep.setTitleContentMode(contentMode);
        }
    }

    /**
     * Load a text content mode value from a step map for a step.
     *
     * @param stepMap the step map
     * @param webStep the step
     */
    protected void loadTextContentMode(LinkedTreeMap stepMap, Step webStep) {
        if (stepMap.get("textContentMode") != null) {
            String textContentMode = (String) stepMap.get("textContentMode");
            Step.ContentMode contentMode = Step.ContentMode.fromId(textContentMode);

            if (contentMode == null) {
                webStep.setTextContentMode(Step.ContentMode.TEXT);
                return;
            }

            webStep.setTextContentMode(contentMode);
        }
    }

    /**
     * Load a scrollTo value from a step map for a step.
     *
     * @param stepMap the step map
     * @param webStep the step
     */
    protected void loadScrollTo(LinkedTreeMap stepMap, Step webStep) {
        if (stepMap.get("scrollTo") != null) {
            String scrollTo = (String) stepMap.get("scrollTo");

            webStep.setScrollTo(Boolean.valueOf(scrollTo));
        }
    }

    /**
     * Load a cancellable value from a step map for a step.
     *
     * @param stepMap the step map
     * @param webStep the step
     */
    protected void loadCancellable(LinkedTreeMap stepMap, Step webStep) {
        if (stepMap.get("cancellable") != null) {
            String cancellable = (String) stepMap.get("cancellable");

            webStep.setCancellable(Boolean.valueOf(cancellable));
        }
    }

    /**
     * Load a modal value from a step map for a step.
     *
     * @param stepMap the step map
     * @param webStep the step
     */
    protected void loadModal(LinkedTreeMap stepMap, Step webStep) {
        if (stepMap.get("modal") != null) {
            String modal = (String) stepMap.get("modal");

            webStep.setModal(Boolean.valueOf(modal));
        }
    }

    /**
     * Load a height value from a step map for a step.
     *
     * @param stepMap the step map
     * @param webStep the step
     */
    protected void loadHeight(LinkedTreeMap stepMap, Step webStep) {
        if (stepMap.get("height") != null) {
            String height = (String) stepMap.get("height");

            webStep.setHeight(height);
        }
    }

    /**
     * Load a width value from a step map for a step.
     *
     * @param stepMap the step map
     * @param webStep the step
     */
    protected void loadWidth(LinkedTreeMap stepMap, Step webStep) {
        if (stepMap.get("width") != null) {
            String width = (String) stepMap.get("width");

            webStep.setWidth(width);
        }
    }

    /**
     * Load a title value from a step map for a step.
     *
     * @param stepMap the step map
     * @param webStep the step
     */
    protected void loadTitle(LinkedTreeMap stepMap, Step webStep) {
        if (stepMap.get("title") != null) {
            String title = (String) stepMap.get("title");

            title = loadResourceString(title);
            webStep.setTitle(title);
        }
    }

    /**
     * Load a text value from a step map for a step.
     *
     * @param stepMap the step map
     * @param webStep the step
     */
    protected void loadText(LinkedTreeMap stepMap, Step webStep) {
        if (stepMap.get("text") != null) {
            String text = (String) stepMap.get("text");

            text = loadResourceString(text);
            webStep.setText(text);
        }
    }

    /**
     * Load a string from a messages pack by a string key.
     *
     * @param stringKey the string key
     * @return the string
     */
    @Nullable
    protected String loadResourceString(String stringKey) {
        if (messagesPack != null) {
            return messages.getMessage(messagesPack, stringKey);
        }

        return stringKey;
    }

    /**
     * Get CUBA component from an extension by a component id.
     *
     * @param attachId the component id
     * @return the CUBA component
     */
    @Nullable
    protected com.haulmont.cuba.gui.components.Component getCubaComponent(String attachId) {
        return ComponentsHelper.findComponent(extension.getFrame(), attachId);
    }

    /**
     * Get a click listener by an action string.
     *
     * @param action the full action string
     * @return the click listener
     */
    @Nullable
    protected Consumer<StepButton.ClickEvent> getClickListener(String action) {
        String[] split = action.split(":");

        if (split.length == 2) {

            String actionId = split[1];
            if (split[0].equals("tour")) {
                return Objects.requireNonNull(TourActionType.fromId(actionId))::execute;
            }

            if (split[0].equals("step")) {
                return Objects.requireNonNull(StepActionType.fromId(actionId))::execute;
            }

        }
        return null;
    }
}
