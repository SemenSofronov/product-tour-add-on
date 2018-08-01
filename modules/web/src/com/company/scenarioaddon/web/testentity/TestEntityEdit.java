package com.company.scenarioaddon.web.testentity;

import com.company.scenarioaddon.web.gui.components.*;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.scenarioaddon.entity.TestEntity;
import com.haulmont.cuba.gui.components.Frame;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.web.theme.HaloTheme;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;

public class TestEntityEdit extends AbstractEditor<TestEntity> {

    protected static int counter = 0;

    @Named("fieldGroup.name")
    protected TextField nameField;

    @Inject
    protected Frame windowActions;

    @Override
    protected void postInit() {
        WebTour webTour = new WebTour();

        WebStep webStep1 = new WebStep();
        webStep1.setTitle("<i>Editor</i>");
        webStep1.setTitleContentMode(Step.ContentMode.HTML);
        webStep1.setText("It is an <i>editor screen</i>.<br>Here you can create or edit entities.");
        webStep1.setTextContentMode(Step.ContentMode.HTML);
        webStep1.addButton(new WebStepButton("Close", HaloTheme.BUTTON_DANGER, WebTourActions::cancel));
        webStep1.addButton(new WebStepButton("Next", HaloTheme.BUTTON_FRIENDLY, WebTourActions::next));

        WebStep webStep2 = new WebStep(nameField);
        webStep2.setTitle("<i>Attribute</i>");
        webStep2.setTitleContentMode(Step.ContentMode.HTML);
        webStep2.setText("It is an <i>attribute field</i>.<br>Fill the field by your own data.");
        webStep2.setTextContentMode(Step.ContentMode.HTML);
        webStep2.setModal(true);
        webStep2.addButton(new WebStepButton("Back", HaloTheme.BUTTON_PRIMARY, WebTourActions::back));
        webStep2.addButton(new WebStepButton("Next", HaloTheme.BUTTON_FRIENDLY, WebTourActions::next));

        WebStep webStep3 = new WebStep(Objects.requireNonNull(windowActions.getComponent(0)));
        webStep3.setTitle("<i>Actions</i>");
        webStep3.setTitleContentMode(Step.ContentMode.HTML);
        webStep3.setText("Its are <i>window actions</i>.<br>Choose what do you want to do with this window.");
        webStep3.setTextContentMode(Step.ContentMode.HTML);
        webStep3.setModal(true);
        webStep3.addButton(new WebStepButton("Close", HaloTheme.BUTTON_PRIMARY, WebTourActions::cancel));
        webStep3.addButton(new WebStepButton("Finish", HaloTheme.BUTTON_FRIENDLY, WebTourActions::next));

        webTour.addStep(webStep1);
        webTour.addStep(webStep2);
        webTour.addStep(webStep3);

        if (TestEntityEdit.counter++ == 0) {
            webTour.startAction().actionPerform(null);
        }
    }
}