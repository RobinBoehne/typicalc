package edu.kit.typicalc.view.content.typeinferencecontent;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.kit.typicalc.model.TypeInfererInterface;
import edu.kit.typicalc.view.content.ControlPanel;
import edu.kit.typicalc.view.content.ControlPanelView;
import edu.kit.typicalc.view.main.MainViewImpl;

@Route(value = "visualize", layout = MainViewImpl.class)
@PageTitle("TypeInferenceView")
@CssImport("./styles/view/type-inference.css")
public class TypeInferenceView extends VerticalLayout
        implements ControlPanelView, ComponentEventListener<AttachEvent> {
    /**
     * Route of this view.
     */
    public static final String ROUTE = "infer";
    private static final String SCROLLER_ID = "scroller";
    private static final String CONTENT_ID = "content";
    private static final String ID = "type-inference-view";

    private int currentStep = 0;


    private MathjaxUnification unification;
    private MathjaxProofTree tree;
    private final transient LatexCreator lc;
    private final Div content;
    private final ShareDialog shareDialog;

    public TypeInferenceView(TypeInfererInterface typeInferer) {
        setId(ID);
        setSizeFull();
        addAttachListener(this);
        lc = new LatexCreator(typeInferer);
        content = new Div();
        content.setId(CONTENT_ID);
        ControlPanel controlPanel = new ControlPanel(this, this);
        Scroller scroller = new Scroller(content);
        scroller.setId(SCROLLER_ID);
        scroller.setScrollDirection(Scroller.ScrollDirection.BOTH);
        setAlignItems(Alignment.CENTER);
        add(scroller, controlPanel);
        setContent();
        shareDialog = new ShareDialog(getTranslation("root.domain")
                + ROUTE + "/"
                + typeInferer.getFirstInferenceStep().getConclusion().getLambdaTerm(),
                lc.getLatexPackages(), lc.getTree());
    }

    private void setContent() {
        // todo implement correctly
        unification = new MathjaxUnification(lc.getUnification());
        tree = new MathjaxProofTree(lc.getTree());
        content.add(unification, tree);
    }

    @Override
    public void shareButton() {
        shareDialog.open();
    }

    private void refreshElements(int currentStep) {
        unification.showStep(currentStep);
        tree.showStep(currentStep < tree.getStepCount() ? currentStep : tree.getStepCount() - 1);
    }

    @Override
    public void firstStepButton() {
        currentStep = currentStep > tree.getStepCount()  && tree.getStepCount() > 0 ? tree.getStepCount() - 1 : 0;
        refreshElements(currentStep);
    }

    @Override
    public void lastStepButton() {
        currentStep = currentStep < tree.getStepCount() - 1 ? tree.getStepCount() - 1 : unification.getStepCount() - 1;
        refreshElements(currentStep);
    }

    @Override
    public void nextStepButton() {
        currentStep = currentStep < unification.getStepCount() - 1 ? currentStep + 1 : currentStep;
        refreshElements(currentStep);
    }

    @Override
    public void previousStepButton() {
        currentStep = currentStep > 0 ? currentStep - 1 : currentStep;
        refreshElements(currentStep);
    }

    @Override
    public void onComponentEvent(AttachEvent attachEvent) {
        currentStep = 0;
        refreshElements(currentStep);
    }
}
