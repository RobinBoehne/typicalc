package edu.kit.typicalc.model.type;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionTypeTest {

    @Test
    void equalsTest() {
        EqualsVerifier.forClass(FunctionType.class).usingGetClass().verify();
    }

    @Test
    void substitute() {
        TypeVariable typeVariable1 = new TypeVariable(TypeVariableKind.USER_INPUT, 1);
        TypeVariable typeVariable2 = new TypeVariable(TypeVariableKind.USER_INPUT, 2);
        TypeVariable typeVariable3 = new TypeVariable(TypeVariableKind.USER_INPUT, 3);

        FunctionType functionType1 = new FunctionType(typeVariable1, typeVariable2);

        FunctionType newType = functionType1.substitute(typeVariable1, typeVariable3);

        assertEquals(newType, new FunctionType(typeVariable3, typeVariable2));
    }

    @Test
    void substitute2() {
        TypeVariable typeVariable1 = new TypeVariable(TypeVariableKind.USER_INPUT, 1);
        TypeVariable typeVariable2 = new TypeVariable(TypeVariableKind.USER_INPUT, 2);
        TypeVariable typeVariable3 = new TypeVariable(TypeVariableKind.USER_INPUT, 3);
        TypeVariable typeVariable4 = new TypeVariable(TypeVariableKind.USER_INPUT, 4);

        FunctionType functionType1 = new FunctionType(typeVariable1, typeVariable2);
        FunctionType functionType2 = new FunctionType(functionType1, typeVariable3);

        FunctionType newType = functionType2.substitute(typeVariable1, typeVariable4);

        assertEquals(newType, new FunctionType(new FunctionType(typeVariable4, typeVariable2), typeVariable3));
    }
}