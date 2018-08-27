package com.haulmont.scenario;

import com.company.scenarioaddon.web.gui.components.Step;
import com.company.scenarioaddon.web.gui.components.StepButton;
import com.company.scenarioaddon.web.gui.components.Tour;
import com.company.scenarioaddon.web.gui.utils.TourParser;
import com.haulmont.cuba.client.testsupport.CubaClientTestCase;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Resources;
import com.haulmont.cuba.core.sys.ResourcesImpl;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.web.gui.WebComponentsFactory;
import com.haulmont.cuba.web.gui.components.WebTextField;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Objects;

@RunWith(JMockit.class)
public class TourParserTest extends CubaClientTestCase {

    @Rule
    public ExpectedException exception = ExpectedException.none();

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
        public com.company.scenarioaddon.web.gui.components.Tour createTour(String json, String messagesPack, Window extension) {
            return super.createTour(json, messagesPack, extension);
        }

        protected void setMessages(Messages messages) {
            this.messages = messages;
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
    public void parseCorrectTourFromJson() {
        String file = Objects.requireNonNull(resources.getResourceAsString("com/haulmont/scenario/json_data/correctTour.json"));

        Window window = componentsFactory.createComponent(Window.class);

        textField1 = new TestTextField();
        textField1.setId("button1");
        window.add(textField1);

        textField2 = new TestTextField();
        textField2.setId("button2");
        window.add(textField2);

        com.company.scenarioaddon.web.gui.components.Tour tour = tourParser.createTour(file,
                "com/haulmont/scenario/messages.properties", window);

        List<Step> steps = tour.getSteps();

        validateFirstStep(steps.get(0));

        validateSecondStep(steps.get(1));
    }

    @Test
    public void validateIncorrectAttachTo() {
        String file = Objects.requireNonNull(resources.getResourceAsString("com/haulmont/scenario/json_data/incorrectAttachTo.json"));

        Window window = componentsFactory.createComponent(Window.class);

        try {
            tourParser.createTour(file, null, window);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "AttachTo id is wrong!");
        }
    }

    @Test
    public void validateIncorrectAnchor() {
        String file = Objects.requireNonNull(resources.getResourceAsString("com/haulmont/scenario/json_data/incorrectAnchor.json"));

        Window window = componentsFactory.createComponent(Window.class);

        try {
            tourParser.createTour(file, null, window);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "Anchor value is wrong!");
        }
    }

    @Test
    public void validateIncorrectTextContentMode() {
        String file = Objects.requireNonNull(resources.getResourceAsString("com/haulmont/scenario/json_data/incorrectTextContentMode.json"));

        Window window = componentsFactory.createComponent(Window.class);

        try {
            tourParser.createTour(file, null, window);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "TextContentMode value is wrong!");
        }
    }

    @Test
    public void validateIncorrectTitleContentMode() {
        String file = Objects.requireNonNull(resources.getResourceAsString("com/haulmont/scenario/json_data/incorrectTitleContentMode.json"));

        Window window = componentsFactory.createComponent(Window.class);

        try {
            tourParser.createTour(file, null, window);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "TitleContentMode value is wrong!");
        }
    }

    @Test
    public void validateIncorrectWidth() {
        String file = Objects.requireNonNull(resources.getResourceAsString("com/haulmont/scenario/json_data/incorrectWidth.json"));

        Window window = componentsFactory.createComponent(Window.class);

        try {
            tourParser.createTour(file, null, window);
        } catch (IllegalArgumentException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    @Test
    public void validateIncorrectHeight() {
        String file = Objects.requireNonNull(resources.getResourceAsString("com/haulmont/scenario/json_data/incorrectHeight.json"));

        Window window = componentsFactory.createComponent(Window.class);

        try {
            tourParser.createTour(file, null, window);
        } catch (IllegalArgumentException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    @Test
    public void validateEmptyId() {
        String file = Objects.requireNonNull(resources.getResourceAsString("com/haulmont/scenario/json_data/emptyId.json"));

        Window window = componentsFactory.createComponent(Window.class);

        Tour tour = tourParser.createTour(file, null, window);
        Step step = tour.getSteps().get(0);
        Assert.assertNotNull(step.getId());
    }

    @Test
    public void validateEmptyCaption() {
        String file = Objects.requireNonNull(resources.getResourceAsString("com/haulmont/scenario/json_data/emptyCaption.json"));

        Window window = componentsFactory.createComponent(Window.class);

        try {
            tourParser.createTour(file, null, window);
        } catch (IllegalArgumentException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    @Test
    public void validateIncorrectAction() {
        String file = Objects.requireNonNull(resources.getResourceAsString("com/haulmont/scenario/json_data/incorrectAction.json"));

        Window window = componentsFactory.createComponent(Window.class);

        try {
            tourParser.createTour(file, null, window);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "Action value is wrong!");
        }
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
    }

    protected void validateFirstStepFirstButton(StepButton firstStepFirstButton) {
        Assert.assertEquals(firstStepFirstButton.getCaption(), "Cancel");
        Assert.assertEquals(firstStepFirstButton.getStyleName(), "danger");
        Assert.assertFalse(firstStepFirstButton.isEnabled());
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

    }

    protected void validateSecondStepSecondButton(StepButton secondStepSecondButton) {
        Assert.assertEquals(secondStepSecondButton.getCaption(), "Finish");
        Assert.assertEquals(secondStepSecondButton.getStyleName(), "friendly");
        Assert.assertTrue(secondStepSecondButton.isEnabled());

    }
}
