package edu.kit.typicalc.view.main;

import java.util.HashMap;

import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import edu.kit.typicalc.view.content.infocontent.StartPageView;
import edu.kit.typicalc.view.main.MainView.MainViewListener;

/**
 * Contains all the components constantly shown in the upper part of the webage.
 */
@CssImport("./styles/view/main/upper-bar.css")
public class UpperBar extends HorizontalLayout {
    
    private static final long serialVersionUID = -7344967027514015830L;
    
    private static final String VIEW_TITLE_ID = "viewTitle";
    private static final String INPUT_BAR_ID = "inputBar";
    private static final String HELP_ICON_ID = "helpIcon";
    private static final String UPPER_BAR_ID = "header";
    
    private final H1 viewTitle;
    private final InputBar inputBar;
    private final Icon helpIcon;
    
    private final transient MainViewListener presenter;
    
    /**
     * Initializes a new UpperBar with the provided mainViewListener.
     * 
     * @param presenter the listener used to communicate with the model
     */
    protected UpperBar(final MainViewListener presenter) {
        this.presenter = presenter;
        
        this.viewTitle = new H1(getTranslation("root.typicalc"));
        viewTitle.setId(VIEW_TITLE_ID);
        this.inputBar = new InputBar(this::typeInfer);
        inputBar.setId(INPUT_BAR_ID);
        this.helpIcon = new Icon(VaadinIcon.QUESTION_CIRCLE);
        helpIcon.addClickListener(event -> onHelpIconClick());
        helpIcon.setId(HELP_ICON_ID);
        
        viewTitle.addClickListener(event -> this.getUI().get().navigate(StartPageView.class));
        
        add(new DrawerToggle(), viewTitle, inputBar, helpIcon);
        setId(UPPER_BAR_ID);
        getThemeList().set("dark", true); //TODO remove magic string
        setWidthFull();
        setSpacing(false);
        setAlignItems(FlexComponent.Alignment.CENTER);
    }
    
    /**
     * Starts the type inference algorithm by passing the required arguments to the MainViewListener.
     * 
     * @param lambdaString the lambda term to be type-inferred
     */
    protected void typeInfer(final String lambdaString) {
        presenter.typeInferLambdaString(lambdaString, new HashMap<>());
    }
    
    private void onHelpIconClick() {
        Dialog helpDialog = new HelpDialog();
        helpDialog.open();
    }    
}
