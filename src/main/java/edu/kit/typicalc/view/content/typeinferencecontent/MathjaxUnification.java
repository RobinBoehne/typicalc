package edu.kit.typicalc.view.content.typeinferencecontent;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import edu.kit.typicalc.view.MathjaxAdapter;

/**
 * Renders the constraints and unification from LaTeX using MathJax and allows step-by-
 * step-revealing capabilities. Relies on MathjaxUnificationJS to interact with MathJax.
 */
@Tag("tc-unification")
@JsModule("./src/mathjax-unification.ts")
public class MathjaxUnification extends LitTemplate implements MathjaxAdapter {

    private int stepCount = -1;

    @Id("tc-content")
    private Div content;

    /**
     * Creates a new HTML element that renders the constraints and unification and
     * calculates the steps.
     * @param latex the LaTeX-String to render with MathJax
     */
    public MathjaxUnification(String latex) {
        content.add(latex);
    }

    @ClientCallable
    private void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    @Override
    public int getStepCount() {
        return this.stepCount;
    }

    @Override
    public void showStep(int n) {
        // todo implement
    }

    @Override
    public void scale(double newScaling) {
        // todo implement
    }
}
