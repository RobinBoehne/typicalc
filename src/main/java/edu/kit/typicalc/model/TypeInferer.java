package edu.kit.typicalc.model;

import edu.kit.typicalc.model.step.InferenceStep;
import edu.kit.typicalc.model.term.LambdaTerm;
import edu.kit.typicalc.model.term.VarTerm;
import edu.kit.typicalc.model.type.Type;
import edu.kit.typicalc.model.type.TypeAbstraction;
import edu.kit.typicalc.model.type.TypeVariableKind;

import java.util.*;

/**
 * The type inferer is responsible for the whole type inference of a given lambda term, taking
 * into account the given type assumptions. It implements the TypeInfererInterface and
 * therefore can hand out an inference step structure, a list of unification steps, the most
 * general unifier and the final type suiting the type inference of the given lambda term. It is
 * guaranteed that this information can be accessed right after instantiation (no additional
 * initialization is required).
 */
public class TypeInferer implements TypeInfererInterface {

    private final Tree tree;
    private final Optional<Unification> unification;
    private final Optional<TypeInferenceResult> typeInfResult;

    /**
     * Initializes a new TypeInferer for the given type assumptions and lambda term.
     * The inference step structure, unification steps, the most general unifier and the
     * final type are generated and calculated here.
     *
     * @param lambdaTerm      the lambda term to generate the tree for
     * @param typeAssumptions the type assumptions to consider when generating the tree
     */
    protected TypeInferer(LambdaTerm lambdaTerm, Map<VarTerm, TypeAbstraction> typeAssumptions) {
        Map<VarTerm, TypeAbstraction> completeTypeAss = createAssForFreeVariables(lambdaTerm);
        completeTypeAss.putAll(typeAssumptions);
        tree = new Tree(completeTypeAss, lambdaTerm);

        // cancel algorithm if a sub-inference failed
        if (tree.hasFailedSubInference()) {
            unification = Optional.empty();
            typeInfResult = Optional.empty();
            return;
        }

        unification = Optional.of(new Unification(new ArrayDeque<>(tree.getConstraints())));

        // cancel algorithm if term can't be typified
        if (unification.get().getSubstitutions().isError()) {
            typeInfResult = Optional.empty();
            return;
        }

        List<Substitution> substitutions = unification.get().getSubstitutions().unwrap();
        typeInfResult = Optional.of(new TypeInferenceResult(substitutions, tree.getFirstTypeVariable()));
    }

    private Map<VarTerm, TypeAbstraction> createAssForFreeVariables(LambdaTerm lambdaTerm) {
        TypeVariableFactory typeVarFactory = new TypeVariableFactory(TypeVariableKind.GENERATED_TYPE_ASSUMPTION);
        Set<VarTerm> freeVariables = lambdaTerm.getFreeVariables();

        Map<VarTerm, TypeAbstraction> generatedTypeAss = new LinkedHashMap<>();
        for (VarTerm varTerm : freeVariables) {
            generatedTypeAss.put(varTerm, new TypeAbstraction(typeVarFactory.nextTypeVariable()));
        }

        return generatedTypeAss;
    }


    @Override
    public InferenceStep getFirstInferenceStep() {
        return tree.getFirstInferenceStep();
    }

    @Override
    public Optional<List<UnificationStep>> getUnificationSteps() {
        return unification.map(Unification::getUnificationSteps);
    }

    @Override
    public Optional<List<Substitution>> getMGU() {
        return typeInfResult.map(TypeInferenceResult::getMGU);
    }

    @Override
    public Optional<Type> getType() {
        return typeInfResult.map(TypeInferenceResult::getType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TypeInferer that = (TypeInferer) o;
        return tree.equals(that.tree) && unification.equals(that.unification)
                && typeInfResult.equals(that.typeInfResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tree, unification, typeInfResult);
    }
}
