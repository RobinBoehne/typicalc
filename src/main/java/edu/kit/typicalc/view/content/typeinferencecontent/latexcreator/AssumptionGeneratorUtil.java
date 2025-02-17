package edu.kit.typicalc.view.content.typeinferencecontent.latexcreator;

import edu.kit.typicalc.model.term.VarTerm;
import edu.kit.typicalc.model.type.TypeAbstraction;

import java.util.Map;

import static edu.kit.typicalc.view.content.typeinferencecontent.latexcreator.LatexCreatorConstants.COLON;
import static edu.kit.typicalc.view.content.typeinferencecontent.latexcreator.LatexCreatorConstants.COMMA;
import static edu.kit.typicalc.view.content.typeinferencecontent.latexcreator.LatexCreatorConstants.DOT_SIGN;
import static edu.kit.typicalc.view.content.typeinferencecontent.latexcreator.LatexCreatorConstants.FOR_ALL;

/**
 * Util class for {@link LatexCreator} and {@link LatexCreatorConstraints} to generate LaTeX code from
 * a Map representing type assumptions.
 */
public final class AssumptionGeneratorUtil {
    private AssumptionGeneratorUtil() {
    }

    /**
     * Converts some type assumptions into LaTeX code to display them.
     * Basic structure: name: Type, otherName: OtherType
     *
     * @param typeAssumptions the type assumptions
     * @param mode latex creator mode
     * @return the latex code
     */
    static String typeAssumptionsToLatex(Map<VarTerm, TypeAbstraction> typeAssumptions,
                                                   LatexCreatorMode mode) {
        if (typeAssumptions.isEmpty()) {
            return "";
        } else {
            StringBuilder assumptions = new StringBuilder();
            typeAssumptions.forEach(((varTerm, typeAbstraction) -> {
                String termLatex = new LatexCreatorTerm(varTerm, mode).getLatex();
                String abstraction = generateTypeAbstraction(typeAbstraction, mode);
                assumptions.append(termLatex)
                        .append(COLON)
                        .append(abstraction)
                        .append(COMMA);
            }));
            assumptions.deleteCharAt(assumptions.length() - 1);
            return assumptions.toString();
        }
    }

    /**
     * Return the latex code for a given type abstraction, e.g.:
     * ∀t1. t1 -> t1
     *
     * @param abs the type abstraction
     * @param mode latex creator mode
     * @return the latex code
     */
    public static String generateTypeAbstraction(TypeAbstraction abs, LatexCreatorMode mode) {
        StringBuilder abstraction = new StringBuilder();
        if (abs.hasQuantifiedVariables()) {
            abstraction.append(FOR_ALL);
            abs.getQuantifiedVariables().forEach(typeVariable -> {
                String variableTex = new LatexCreatorType(typeVariable, mode).getLatex();
                abstraction.append(variableTex).append(COMMA);
            });
            abstraction.deleteCharAt(abstraction.length() - 1);
            abstraction.append(DOT_SIGN);
        }
        abstraction.append(new LatexCreatorType(abs.getInnerType(), mode).getLatex());
        return abstraction.toString();
    }
}
