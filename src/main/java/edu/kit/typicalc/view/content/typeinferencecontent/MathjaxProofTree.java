package edu.kit.typicalc.view.content.typeinferencecontent;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import edu.kit.typicalc.view.MathjaxAdapter;

/**
 * Renders a tree from LaTeX using MathJax and allows for step-by-step-revealing for
 * proof trees that use the bussproofs package. Relies on MathjaxProofTreeJS to interact
 * with MathJax.
 */
@Tag("tc-proof-tree")
@JsModule("./src/mathjax-proof-tree.ts")
public class MathjaxProofTree extends LitTemplate implements MathjaxAdapter {

    @Id("tc-content")
    private Div content;

    /**
     * Creates a new HTML element that renders the proof tree and cuts it into steps.
     * The latex String must consist of exactly one proof tree environment in order for
     * this element to work. In other cases the expected behaviour is undefined.
     * @param latex the LaTeX-String to render with MathJax
     */
    public MathjaxProofTree(String latex) {
        content.add(latex);
    }

    @Override
    public int getStepCount() {
        return 0;
    }

    @Override
    public void showStep(int n) {

    }

    @Override
    public void scale(double newScaling) {

    }
}

