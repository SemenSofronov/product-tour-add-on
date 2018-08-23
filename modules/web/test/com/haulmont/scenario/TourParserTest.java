package com.haulmont.scenario;

import com.company.scenarioaddon.web.gui.components.Step;
import com.company.scenarioaddon.web.gui.components.StepButton;
import com.company.scenarioaddon.web.gui.components.WebTour;
import com.company.scenarioaddon.web.gui.utils.TourParser;
import com.haulmont.cuba.client.testsupport.CubaClientTestCase;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Resources;
import com.haulmont.cuba.core.sys.ResourcesImpl;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.web.gui.WebComponentsFactory;
import com.haulmont.cuba.web.gui.components.WebButtonsPanel;
import com.haulmont.cuba.web.gui.components.WebTextField;
import com.vaadin.server.AbstractClientConnector;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vaadin.addons.producttour.tour.Tour;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@RunWith(JMockit.class)
public class TourParserTest extends CubaClientTestCase {

    protected Resources resources;
    protected TestTourParser tourParser;
    protected WebComponentsFactory componentsFactory;

    protected TestTextField textField1;
    protected TestTextField textField2;

    @Before
    public void init() {
        setupInfrastructure();
        resources = new ResourcesImpl(getClass().getClassLoader(), null);
        tourParser = new TestTourParser();
        tourParser.setMessages(messages);
        componentsFactory = new WebComponentsFactory();
    }

    protected class TestTourParser extends TourParser {

        @Override
        public com.company.scenarioaddon.web.gui.components.Tour createTour(String json, String messagesPack,
                                                                            Component extensionFor) {

            initTourParser(messagesPack, extensionFor);

            com.company.scenarioaddon.web.gui.components.Tour tour = new TestTour(extensionFor);
            loadTour(json, tour);
            return tour;
        }

        protected void setMessages(Messages messages) {
            this.messages = messages;
        }

        @Override
        protected void initTourParser(String messagesPack, Component extensionFor) {
            this.messagesPack = messagesPack;
            extension = extensionFor;
        }
    }

    protected class TestTour extends WebTour {

        /**
         * Construct a new tour.
         */
        TestTour(Component component) {
            super(component);
        }

        class TestCubaTour extends Tour {
            @Override
            protected void extend(AbstractClientConnector target) {
            }
        }

        @Override
        protected Tour createExtension(Component component) {
            return new TestCubaTour();
        }
    }

    protected class TestTextField extends WebTextField {
        @Override
        public void setId(String id) {
            if (!Objects.equals(this.id, id)) {
                if (frame != null) {
                    frame.unregisterComponent(this);
                }

                this.id = id;
                if (this.component != null) {
                    this.component.setCubaId(id);
                }

                if (frame != null) {
                    frame.registerComponent(this);
                }
            }
        }
    }

    @Test
    public void parseTourFromJson() {
        String file = Objects.requireNonNull(resources.getResourceAsString("com/haulmont/scenario/mytour"));

        Component.Container container = componentsFactory.createComponent(WebButtonsPanel.class);

        textField1 = new TestTextField();
        textField1.setId("button1");
        container.add(textField1);

        textField2 = new TestTextField();
        textField2.setId("button2");
        container.add(textField2);

        com.company.scenarioaddon.web.gui.components.Tour tour = tourParser.createTour(file,
                "com/haulmont/scenario/messages.properties", container);

        List<Step> steps = tour.getSteps();

        validateFirstStep(steps.get(0));

        validateSecondStep(steps.get(1));
    }

    protected void validateFirstStep(Step firstStep) {
        Assert.assertEquals(firstStep.getId(), "step1");
        Assert.assertEquals(firstStep.getAnchor(), Step.StepAnchor.LEFT);
        Assert.assertEquals(firstStep.getAttachedTo(), textField1);

        Assert.assertEquals(firstStep.getHeight(), 600, 0);
        Assert.assertEquals(firstStep.getWidth(), 400, 0);

        Assert.assertEquals(firstStep.getText(), messages.getMessage("com/haulmont/scenario/messages.properties",
                "tour.createButtonText"));
        Assert.assertEquals(firstStep.getTitle(), messages.getMessage("com/haulmont/scenario/messages.properties",
                "tour.title"));
        Assert.assertEquals(firstStep.getTextContentMode(), Step.ContentMode.HTML);
        Assert.assertEquals(firstStep.getTitleContentMode(), Step.ContentMode.HTML);

        Assert.assertTrue(firstStep.isCancellable());
        Assert.assertTrue(firstStep.isScrollTo());
        Assert.assertFalse(firstStep.isModal());
        Assert.assertFalse(firstStep.isVisible());

        List<StepButton> firstStepButtons = firstStep.getButtons();
        Assert.assertEquals(firstStepButtons.size(), 2);

        validateFirstStepFirstButton(firstStepButtons.get(0));

        validateFirstStepSecondButton(firstStepButtons.get(1));
    }

    protected void validateFirstStepSecondButton(StepButton firstStepSecondButton) {
        Assert.assertEquals(firstStepSecondButton.getCaption(), "Next");
        Assert.assertEquals(firstStepSecondButton.getStyleName(), "primary");

        Assert.assertTrue(firstStepSecondButton.isEnabled());
        List<Consumer<StepButton.ClickEvent>> clickListeners = firstStepSecondButton.getClickListeners();
        Assert.assertEquals(clickListeners.size(), 1);
    }

    protected void validateFirstStepFirstButton(StepButton firstStepFirstButton) {
        Assert.assertEquals(firstStepFirstButton.getCaption(), "Cancel");
        Assert.assertEquals(firstStepFirstButton.getStyleName(), "danger");

        Assert.assertFalse(firstStepFirstButton.isEnabled());
        List<Consumer<StepButton.ClickEvent>> clickListeners = firstStepFirstButton.getClickListeners();
        Assert.assertEquals(clickListeners.size(), 1);
    }

    protected void validateSecondStep(Step secondStep) {
        Assert.assertEquals(secondStep.getId(), "step2");
        Assert.assertEquals(secondStep.getAnchor(), Step.StepAnchor.TOP);
        Assert.assertEquals(secondStep.getAttachedTo(), textField2);

        Assert.assertEquals(secondStep.getText(), "text");
        Assert.assertEquals(secondStep.getTitle(), "Title");
        Assert.assertEquals(secondStep.getTextContentMode(), Step.ContentMode.TEXT);
        Assert.assertEquals(secondStep.getTitleContentMode(), Step.ContentMode.TEXT);

        Assert.assertFalse(secondStep.isCancellable());
        Assert.assertFalse(secondStep.isScrollTo());
        Assert.assertFalse(secondStep.isModal());
        Assert.assertFalse(secondStep.isVisible());

        List<StepButton> secondStepButtons = secondStep.getButtons();
        Assert.assertEquals(secondStepButtons.size(), 2);

        validateSecondStepFirstButton(secondStepButtons.get(0));

        validateSecondStepSecondButton(secondStepButtons.get(1));
    }

    protected void validateSecondStepFirstButton(StepButton secondStepFirstButton) {
        Assert.assertEquals(secondStepFirstButton.getCaption(), "Back");
        Assert.assertEquals(secondStepFirstButton.getStyleName(), "primary");

        Assert.assertTrue(secondStepFirstButton.isEnabled());
        List<Consumer<StepButton.ClickEvent>> clickListeners = secondStepFirstButton.getClickListeners();
        Assert.assertEquals(clickListeners.size(), 1);

    }

    protected void validateSecondStepSecondButton(StepButton secondStepSecondButton) {
        Assert.assertEquals(secondStepSecondButton.getCaption(), "Finish");
        Assert.assertEquals(secondStepSecondButton.getStyleName(), "friendly");

        Assert.assertTrue(secondStepSecondButton.isEnabled());
        List<Consumer<StepButton.ClickEvent>> clickListeners = secondStepSecondButton.getClickListeners();
        Assert.assertEquals(clickListeners.size(), 1);

    }
}
