/*
 * Copyright (c) 2008-2018 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.company.scenarioaddon.web.gui.utils;

import com.company.scenarioaddon.web.gui.components.*;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.haulmont.cuba.core.global.Messages;

import java.util.ArrayList;
import java.util.function.Consumer;

public class TourParser {

    private static TourParser instance = new TourParser();

    private TourParser() {

    }

    public static TourParser getInstance() {
        return instance;
    }

    public Tour createTour(String json, Messages messages, String messagePack) {
        ArrayList steps = new Gson().fromJson(json, ArrayList.class);
        Tour tour = new WebTour();
        for (Object step: steps) {
            LinkedTreeMap stepMap = (LinkedTreeMap) step;
            String id = stepMap.get("id").toString();
            String text = (String) stepMap.get("text");
            String title = (String) stepMap.get("title");
            String width = (String) stepMap.get("width");
            String height = (String) stepMap.get("height");
            String textContentMode = (String) stepMap.get("textContentMode");
            String titleContentMode = (String) stepMap.get("titleContentMode");
            String cancellable = (String) stepMap.get("cancellable");
            String modal = (String) stepMap.get("modal");
            String localedText = messages.getMessage(messagePack, text);
            String localedTitle = messages.getMessage(messagePack, title);
            Step webStep = new WebStep(id);
            webStep.setText(localedText);
            webStep.setTitle(localedTitle);
            webStep.setWidth(width);
            webStep.setHeight(height);
            if (textContentMode != null) {
                webStep.setTextContentMode(getContentMode(textContentMode));
            }
            if (titleContentMode != null) {
                webStep.setTitleContentMode(getContentMode(titleContentMode));
            }
            webStep.setModal(Boolean.valueOf(modal));
            webStep.setCancellable(Boolean.valueOf(cancellable));
            ArrayList buttons = (ArrayList) stepMap.get("buttons");
            for (Object button: buttons) {
                LinkedTreeMap buttonMap = (LinkedTreeMap) button;
                String caption = (String) buttonMap.get("caption");
                String style = (String) buttonMap.get("style");
                String action = (String) buttonMap.get("action");
                String localedCaption = messages.getMessage(messagePack, caption);
                StepButton stepButton = new WebStepButton(localedCaption);
                stepButton.addStyleName(style);
                Consumer<StepButton.ClickEvent> clickListener = getClickListener(action);
                stepButton.addStepButtonClickListener(clickListener);
                webStep.addButton(stepButton);
            }
            tour.addStep(webStep);
        }
        return tour;
    }

    protected Step.ContentMode getContentMode(String contentMode) {
        switch (contentMode) {
            case "HTML": {
                return Step.ContentMode.HTML;
            }
            case "PREFORMATTED": {
                return Step.ContentMode.PREFORMATTED;
            }
            case "TEXT": {
                return Step.ContentMode.TEXT;
            }
            default: {
                return Step.ContentMode.TEXT;
            }
        }
    }

    protected Consumer<StepButton.ClickEvent> getClickListener(String action) {
        switch (action) {
            case "next": {
                return TourActions::next;
            }
            case "cancel": {
                return TourActions::cancel;
            }
            case "hide": {
                return TourActions::hide;
            }
            case "back": {
                return TourActions::back;
            }
            case "start": {
                return TourActions::start;
            }
            default: {
                return null;
            }
        }
    }
}
