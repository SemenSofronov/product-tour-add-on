package com.company.scenarioaddon.web.testentity;

import com.company.scenarioaddon.entity.TestEntity;
import com.company.scenarioaddon.web.gui.components.*;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.web.theme.HaloTheme;

import javax.inject.Inject;
import java.util.Map;
import java.util.Objects;

public class TestEntityBrowse extends AbstractLookup {

    @Inject
    protected Button tutorialButton;

    @Inject
    protected Button createBtn;
    @Inject
    protected Button editBtn;
    @Inject
    protected Button removeBtn;
    @Inject
    protected Filter filter;
    @Inject
    protected GroupTable<TestEntity> testEntitiesTable;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        WebTour webTour = new WebTour();

        tutorialButton.setAction(webTour.startAction());

        WebStep webStep1 = new WebStep("step1", tutorialButton, Step.StepAnchor.RIGHT);
        webStep1.setTitle("Tutorial tour is started!");
        webStep1.setText("Welcome to our tutorial tour! Let us see how you can use it.");
        webStep1.addButton(new WebStepButton("Cancel", HaloTheme.BUTTON_DANGER, WebTourActions::cancel));
        webStep1.addButton(new WebStepButton("Next", HaloTheme.BUTTON_FRIENDLY, WebTourActions::next));

        WebStep webStep2 = new WebStep("step2", createBtn, Step.StepAnchor.RIGHT);
        webStep2.setTitle("<i>Create Button</i>");
        webStep2.setTitleContentMode(Step.ContentMode.HTML);
        webStep2.setText("It is a <i>create button</i>.<br>To create a new entity you may to press the button.");
        webStep2.setTextContentMode(Step.ContentMode.HTML);
        webStep2.addButton(new WebStepButton("Back", HaloTheme.BUTTON_PRIMARY, WebTourActions::back));
        webStep2.addButton(new WebStepButton("Next", HaloTheme.BUTTON_FRIENDLY, WebTourActions::next));

        WebStep webStep3 = new WebStep("step3", editBtn, Step.StepAnchor.RIGHT);
        webStep3.setTitle("<i>Edit Button</i>");
        webStep3.setTitleContentMode(Step.ContentMode.HTML);
        webStep3.setText("It is an <i>edit button</i>.<br>To edit an entity you may to select it in the table and " +
                "press the button.");
        webStep3.setTextContentMode(Step.ContentMode.HTML);
        webStep3.addButton(new WebStepButton("Back", HaloTheme.BUTTON_PRIMARY, WebTourActions::back));
        webStep3.addButton(new WebStepButton("Next", HaloTheme.BUTTON_FRIENDLY, WebTourActions::next));

        WebStep webStep4 = new WebStep("step4", removeBtn, Step.StepAnchor.RIGHT);
        webStep4.setTitle("<i>Remove Button</i>");
        webStep4.setTitleContentMode(Step.ContentMode.HTML);
        webStep4.setText("It is a <i>remove button</i>.<br>To remove an entity you may to select it in the table and " +
                "press the button.");
        webStep4.setTextContentMode(Step.ContentMode.HTML);
        webStep4.addButton(new WebStepButton("Back", HaloTheme.BUTTON_PRIMARY, WebTourActions::back));
        webStep4.addButton(new WebStepButton("Next", HaloTheme.BUTTON_FRIENDLY, WebTourActions::next));

        WebStep webStep5 = new WebStep("step5", filter, Step.StepAnchor.BOTTOM);
        webStep5.setTitle("<i>Filter Panel</i>");
        webStep5.setTitleContentMode(Step.ContentMode.HTML);
        webStep5.setText("It is a <i>filter panel</i>.<br>You may use it to filter the entities in the table.");
        webStep5.setTextContentMode(Step.ContentMode.HTML);
        webStep5.setModal(true);
        webStep5.addButton(new WebStepButton("Back", HaloTheme.BUTTON_PRIMARY, WebTourActions::back));
        webStep5.addButton(new WebStepButton("Go to Editor Screen", HaloTheme.BUTTON_FRIENDLY, event -> {
            Action action = testEntitiesTable.getAction("create");
            Objects.requireNonNull(action).actionPerform(null);
            WebTourActions.next(() -> webTour);
        }));

        WebStep webStep6 = new WebStep("step6", tutorialButton, Step.StepAnchor.RIGHT);
        webStep6.setTitle("<i>Editor</i>");
        webStep6.setTitleContentMode(Step.ContentMode.HTML);
        webStep6.setText("It is an <i>editor screen</i>.<br>Here you can create or edit entities.");
        webStep6.setTextContentMode(Step.ContentMode.HTML);
        webStep6.addButton(new WebStepButton("Finish", HaloTheme.BUTTON_FRIENDLY, WebTourActions::next));


        webTour.addStep(webStep1);
        webTour.addStep(webStep2);
        webTour.addStep(webStep3);
        webTour.addStep(webStep4);
        webTour.addStep(webStep5);
        webTour.addStep(webStep6);
    }
}