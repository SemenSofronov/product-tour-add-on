package com.haulmont.scenario;

import com.company.scenarioaddon.web.gui.utils.TourParser;
import com.google.gson.internal.LinkedTreeMap;
import com.haulmont.cuba.client.sys.MessagesClientImpl;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Resources;
import com.haulmont.cuba.core.sys.ResourcesImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Objects;

public class TourParserTest {

    protected Resources resources;
    protected Messages messages;
    protected TourParser tourParser;

    @Before
    public void initTest() {
        resources = new ResourcesImpl(getClass().getClassLoader(), null);
        messages = new MessagesClientImpl();
        tourParser = new TourParser();
    }

    @Test
    public void parseTour() {
        String file = Objects.requireNonNull(resources.getResourceAsString("com/haulmont/scenario/mytour"));
        tourParser.setSteps(file);

        ArrayList steps = tourParser.getSteps();
        Assert.assertEquals(steps.size(), 2);

        LinkedTreeMap firstStep = (LinkedTreeMap) steps.get(0);

        parseFirstStep(firstStep);

        LinkedTreeMap secondStep = (LinkedTreeMap) steps.get(1);

        parseSecondStep(secondStep);
    }

    protected void parseSecondStep(LinkedTreeMap secondStep) {
        ArrayList buttons = tourParser.getButtons(secondStep);
        Assert.assertNotEquals(buttons.size(), 3);

        String cancellable = tourParser.getCancellable(secondStep);
        Assert.assertNotEquals(cancellable, "true");

        String height = tourParser.getHeight(secondStep);
        Assert.assertNotEquals(height, "600");

        String width = tourParser.getWidth(secondStep);
        Assert.assertNotEquals(width, "400");

        String id = tourParser.getId(secondStep);
        Assert.assertNotEquals(id, "step1");

        String modal = tourParser.getModal(secondStep);
        Assert.assertNull(modal);

        String scrollTo = tourParser.getScrollTo(secondStep);
        Assert.assertNotEquals(scrollTo, "true");

        String text = tourParser.getText(secondStep);
        Assert.assertNotEquals(text, "<i>text</i>");

        String textContentMode = tourParser.getTextContentMode(secondStep);
        Assert.assertNotEquals(textContentMode, "HTML");

        String title = tourParser.getTitle(secondStep);
        Assert.assertNotEquals(title, "Test <i>Tour</i>");

        String titleContentMode = tourParser.getTitleContentMode(secondStep);
        Assert.assertNotEquals(titleContentMode, "HTML");

        LinkedTreeMap firstButton = (LinkedTreeMap) buttons.get(0);

        parseFirstButtonInSecondStep(firstButton);

        LinkedTreeMap secondButton = (LinkedTreeMap) buttons.get(1);

        parseSecondButtonInSecondStep(secondButton);
    }

    protected void parseSecondButtonInSecondStep(LinkedTreeMap secondButton) {
        String action = tourParser.getAction(secondButton);
        Assert.assertEquals(action, "step:complete");

        String caption = tourParser.getCaption(secondButton);
        Assert.assertEquals(caption, "Finish");

        String style = tourParser.getStyle(secondButton);
        Assert.assertEquals(style, "friendly");

        String enabled = tourParser.getEnabled(secondButton);
        Assert.assertEquals(enabled, "true");
    }

    protected void parseFirstButtonInSecondStep(LinkedTreeMap firstButton) {
        String action = tourParser.getAction(firstButton);
        Assert.assertEquals(action, "tour:back");

        String caption = tourParser.getCaption(firstButton);
        Assert.assertEquals(caption, "Back");

        String style = tourParser.getStyle(firstButton);
        Assert.assertEquals(style, "primary");

        String enabled = tourParser.getEnabled(firstButton);
        Assert.assertEquals(enabled, "true");
    }

    protected void parseFirstStep(LinkedTreeMap firstStep) {
        ArrayList buttons = tourParser.getButtons(firstStep);
        Assert.assertEquals(buttons.size(), 2);

        String cancellable = tourParser.getCancellable(firstStep);
        Assert.assertEquals(cancellable, "true");

        String height = tourParser.getHeight(firstStep);
        Assert.assertEquals(height, "600");

        String width = tourParser.getWidth(firstStep);
        Assert.assertEquals(width, "400");

        String id = tourParser.getId(firstStep);
        Assert.assertEquals(id, "step1");

        String modal = tourParser.getModal(firstStep);
        Assert.assertNull(modal);

        String scrollTo = tourParser.getScrollTo(firstStep);
        Assert.assertEquals(scrollTo, "true");

        String text = tourParser.getText(firstStep);
        Assert.assertEquals(text, "<i>text</i>");

        String textContentMode = tourParser.getTextContentMode(firstStep);
        Assert.assertEquals(textContentMode, "HTML");

        String title = tourParser.getTitle(firstStep);
        Assert.assertEquals(title, "Test <i>Tour</i>");

        String titleContentMode = tourParser.getTitleContentMode(firstStep);
        Assert.assertEquals(titleContentMode, "HTML");

        LinkedTreeMap firstButton = (LinkedTreeMap) buttons.get(0);

        parseFirstButtonInFirstStep(firstButton);

        LinkedTreeMap secondButton = (LinkedTreeMap) buttons.get(1);

        parseSecondButtonInFirstStep(secondButton);
    }

    protected void parseSecondButtonInFirstStep(LinkedTreeMap secondButton) {
        String action = tourParser.getAction(secondButton);
        Assert.assertEquals(action, "tour:next");

        String caption = tourParser.getCaption(secondButton);
        Assert.assertEquals(caption, "Next");

        String style = tourParser.getStyle(secondButton);
        Assert.assertEquals(style, "primary");

        String enabled = tourParser.getEnabled(secondButton);
        Assert.assertEquals(enabled, "true");
    }

    protected void parseFirstButtonInFirstStep(LinkedTreeMap firstButton) {
        String action = tourParser.getAction(firstButton);
        Assert.assertEquals(action, "tour:cancel");

        String caption = tourParser.getCaption(firstButton);
        Assert.assertEquals(caption, "Cancel");

        String style = tourParser.getStyle(firstButton);
        Assert.assertEquals(style, "danger");

        String enabled = tourParser.getEnabled(firstButton);
        Assert.assertEquals(enabled, "false");

    }
}
