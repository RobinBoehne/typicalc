package edu.kit.typicalc.model;

import edu.kit.typicalc.model.parser.LambdaParser;
import edu.kit.typicalc.model.parser.ParseError;
import edu.kit.typicalc.model.term.LambdaTerm;
import edu.kit.typicalc.util.Result;

import java.util.Map;

/**
 * Accepts user input and returns a type inference result.
 */
public class ModelImpl implements Model {

    /**
     *Parses the user input given as the lambdaTerm and typeAssumptions and creates
     * a TypeInferer object.
     * @param lambdaTerm the lambda term to type-infer
     * @param typeAssumptions the type assumptions to use
     * @return A TypeInferer object that has calculated the type Inference for the given Lambda Term
     * and type Assumptions
     *
     */
    @Override
    public Result<TypeInfererInterface, ParseError> getTypeInferer(String lambdaTerm,
                                                                   Map<String, String> typeAssumptions) {
        // Parse Lambda Term
        LambdaParser parser = new LambdaParser(lambdaTerm);
        Result<LambdaTerm, ParseError> result = parser.parse();
        if (result.isError()) {
            return new Result<>(null, result.unwrapError());
        }
        //TODO: Parse Type Assumptions and add list to typeInferer

        //Create and return TypeInferer
        TypeInferer typeInferer = new TypeInferer(result.unwrap(), null);
        return new Result<>(typeInferer, null);
    }
}