/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.utils;

import com.company.scenarioaddon.web.gui.components.*;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.haulmont.cuba.core.global.Messages;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

@Component("scenarioaddon_TourParser")
public class TourParser {

    protected static Gson gson = new Gson();

    protected static final Map<String, Step.ContentMode> contentModeMap =
            ImmutableMap.<String, Step.ContentMode>builder()
                    .put("HTML", Step.ContentMode.HTML)
                    .put("PREFORMATTED", Step.ContentMode.PREFORMATTED)
                    .put("TEXT", Step.ContentMode.TEXT)
                    .build();

    protected static final Map<String, TourActionType> tourActionMap =
            ImmutableMap.<String, TourActionType>builder()
                    .put("back", TourActionType.BACK)
                    .put("cancel", TourActionType.CANCEL)
                    .put("hide", TourActionType.HIDE)
                    .put("start", TourActionType.START)
                    .put("next", TourActionType.NEXT)
                    .build();

    protected static final Map<String, StepActionType> stepActionMap =
            ImmutableMap.<String, StepActionType>builder()
                    .put("complete", StepActionType.COMPLETE)
                    .put("cancel", StepActionType.CANCEL)
                    .put("hide", StepActionType.HIDE)
                    .put("scrollTo", StepActionType.SCROLLTO)
                    .put("show", StepActionType.SHOW)
                    .build();

    protected Messages messages;

    protected String messagePack;

    public Tour loadTour(String json, Messages messages, String messagePack) {

        this.messagePack = messagePack;
        this.messages = messages;

        Tour tour = new WebTour();

        ArrayList steps = gson.fromJson(json, ArrayList.class);
        for (Object step : steps) {

            LinkedTreeMap stepMap = (LinkedTreeMap) step;
            Step webStep = loadStep(stepMap);

            ArrayList buttons = (ArrayList) stepMap.get("buttons");
            for (Object button : buttons) {
                StepButton stepButton = loadStepButton(button);
                webStep.addButton(stepButton);
            }

            tour.addStep(webStep);
        }
        return tour;
    }

    protected Step loadStep(LinkedTreeMap stepMap) {

        String id = stepMap.get("id").toString();
        String text = (String) stepMap.get("text");
        String title = (String) stepMap.get("title");
        String width = (String) stepMap.get("width");
        String height = (String) stepMap.get("height");
        String textContentMode = (String) stepMap.get("textContentMode");
        String titleContentMode = (String) stepMap.get("titleContentMode");
        String cancellable = (String) stepMap.get("cancellable");
        String modal = (String) stepMap.get("modal");
        String scrollTo = (String) stepMap.get("scrollTo");

        String localedText = messages.getMessage(messagePack, text);
        String localedTitle = messages.getMessage(messagePack, title);

        Step webStep = new WebStep(id);

        webStep.setText(localedText);
        webStep.setTitle(localedTitle);
        webStep.setWidth(width);
        webStep.setHeight(height);
        webStep.setModal(Boolean.valueOf(modal));
        webStep.setCancellable(Boolean.valueOf(cancellable));
        webStep.setScrollTo(Boolean.valueOf(scrollTo));

        if (textContentMode != null) {
            webStep.setTextContentMode(getContentMode(textContentMode));
        }

        if (titleContentMode != null) {
            webStep.setTitleContentMode(getContentMode(titleContentMode));
        }

        return webStep;
    }

    protected StepButton loadStepButton(Object button) {
        LinkedTreeMap buttonMap = (LinkedTreeMap) button;

        String caption = (String) buttonMap.get("caption");
        String style = (String) buttonMap.get("style");
        String action = (String) buttonMap.get("action");
        String enabled = (String) buttonMap.get("enabled");

        String localedCaption = messages.getMessage(messagePack, caption);

        StepButton stepButton = new WebStepButton(localedCaption);
        stepButton.addStyleName(style);
        stepButton.setEnabled(Boolean.valueOf(enabled));

        Consumer<StepButton.ClickEvent> clickListener = getClickListener(action);
        if (clickListener != null) {
            stepButton.addStepButtonClickListener(clickListener);
        }

        return stepButton;
    }

    protected Step.ContentMode getContentMode(String contentMode) {
        if (contentModeMap.containsKey(contentMode)) {
            return contentModeMap.get(contentMode);
        } else {
            return contentModeMap.get("TEXT");
        }
    }

    protected Consumer<StepButton.ClickEvent> getClickListener(String action) {
        String[] split = action.split(":");

        if (split.length == 2) {

            if (split[0].equals("tour")) {
                String actionName = split[1];
                if (tourActionMap.containsKey(actionName)) {
                    return clickEvent -> tourActionMap.get(actionName).execute(clickEvent);
                }
            }

            if (split[0].equals("step")) {
                String actionName = split[1];
                if (stepActionMap.containsKey(actionName)) {
                    return clickEvent -> stepActionMap.get(actionName).execute(clickEvent);
                }
            }

        }
        return null;
    }
}
