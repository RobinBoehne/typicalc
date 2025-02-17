package edu.kit.typicalc.view.content;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;

/**
 * Provides a GUI in form of buttons for the user to navigate through steps.
 */
@CssImport("./styles/view/control-panel.css")
public class ControlPanel extends HorizontalLayout implements LocaleChangeObserver {

    private static final String ATTRIBUTE_TITLE = "title";

    private static final String ID = "control-panel";

    private final Button firstStep;
    private static final String FIRST_STEP_ID = "first-step";
    private final Button lastStep;
    private static final String LAST_STEP_ID = "last-step";
    private final Button nextStep;
    private static final String NEXT_STEP_ID = "next-step";
    private final Button previousStep;
    private static final String PREVIOUS_STEP_ID = "previous-step";
    private final Button share;
    private static final String SHARE_ID = "share";

    /**
     * Sets up buttons with click-listeners that call the corresponding method in the view.
     *
     * @param view the view that reacts to the button clicks
     */
    public ControlPanel(ControlPanelView view) {
        setId(ID);
        firstStep = new Button(new Icon(VaadinIcon.ANGLE_DOUBLE_LEFT), evt -> view.firstStepButton());
        firstStep.setId(FIRST_STEP_ID);
        lastStep = new Button(new Icon(VaadinIcon.ANGLE_DOUBLE_RIGHT), evt -> view.lastStepButton());
        lastStep.setId(LAST_STEP_ID);
        nextStep = new Button(new Icon(VaadinIcon.ANGLE_RIGHT), evt -> view.nextStepButton());
        nextStep.setId(NEXT_STEP_ID);
        previousStep = new Button(new Icon(VaadinIcon.ANGLE_LEFT), evt -> view.previousStepButton());
        previousStep.setId(PREVIOUS_STEP_ID);
        share = new Button(new Icon(VaadinIcon.CONNECT), evt -> view.shareButton());
        share.setId(SHARE_ID);

        add(share, firstStep, previousStep, nextStep, lastStep);
    }

    /**
     * Enables the firstStep-button if the parameter is true, disables it if hte parameter is false.
     *
     * @param setEnabled true to enable the button,false to disable it
     */
    public void setEnabledFirstStep(boolean setEnabled) {
        firstStep.setEnabled(setEnabled);
    }

    /**
     * Enables the lastStep-button if the parameter is true, disables it if hte parameter is false.
     *
     * @param setEnabled true to enable the button,false to disable it
     */
    public void setEnabledLastStep(boolean setEnabled) {
        lastStep.setEnabled(setEnabled);
    }

    /**
     * Enables the nextStep-button if the parameter is true, disables it if hte parameter is false.
     *
     * @param setEnabled true to enable the button,false to disable it
     */
    public void setEnabledNextStep(boolean setEnabled) {
        nextStep.setEnabled(setEnabled);
    }

    /**
     * Enables the previousStep-button if the parameter is true, disables it if hte parameter is false.
     *
     * @param setEnabled true to enable the button,false to disable it
     */
    public void setEnabledPreviousStep(boolean setEnabled) {
        previousStep.setEnabled(setEnabled);
    }

    /**
     * Enables the share-button if the parameter is true, disables it if hte parameter is false.
     *
     * @param setEnabled true to enable the button,false to disable it
     */
    public void setEnabledShareButton(boolean setEnabled) {
        share.setEnabled(setEnabled);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        share.getElement().setAttribute(ATTRIBUTE_TITLE, getTranslation("root.shareButtonTooltip"));
        firstStep.getElement().setAttribute(ATTRIBUTE_TITLE, getTranslation("root.firstStepTooltip"));
        previousStep.getElement().setAttribute(ATTRIBUTE_TITLE, getTranslation("root.previousStepTooltip"));
        nextStep.getElement().setAttribute(ATTRIBUTE_TITLE, getTranslation("root.nextStepTooltip"));
        lastStep.getElement().setAttribute(ATTRIBUTE_TITLE, getTranslation("root.lastStepTooltip"));
    }
}
