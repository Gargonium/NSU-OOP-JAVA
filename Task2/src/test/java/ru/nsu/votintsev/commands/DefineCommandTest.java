package ru.nsu.votintsev.commands;

import jdk.jfr.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nsu.votintsev.Context;
import ru.nsu.votintsev.exceptions.ValueOfVariableMustBeNumericException;
import ru.nsu.votintsev.exceptions.VariableNameMustStartWithLetterException;
import ru.nsu.votintsev.exceptions.WrongNumberOfArgumentsException;

import static org.junit.jupiter.api.Assertions.*;

class DefineCommandTest {
    Context ctx;
    DefineCommand defineCommand;

    @BeforeEach
    public void setUp() {
        ctx = new Context();
        defineCommand = new DefineCommand();
    }

    @ParameterizedTest(name = "{index} - created var with value {0}")
    @ValueSource(strings = {"first 1", "second 45"})
    public void shouldCreateVariable(String varAndVal) {
        ctx.setArgs(("DEFINE " + varAndVal).split(" ") );
        assertDoesNotThrow(() -> defineCommand.execute(ctx));
        assertDoesNotThrow(() -> assertEquals(Double.parseDouble(varAndVal.split(" ")[1]),
                ctx.getVarFromMap(varAndVal.split(" ")[0])));
    }

    @Nested
    class ThrowExceptionsTests {

        @Test
        @Name("Wrong Number of Arguments Test")
        public void shouldThrowWrongNumberOfArgumentsException() {
            ctx.setArgs("DEFINE name anotherName 123".split(" "));
            assertThrows(WrongNumberOfArgumentsException.class, () -> defineCommand.execute(ctx));
        }

        @Test
        @Name("Not Numeric Value Test")
        public void shouldThrowValueMustBeNumericException() {
            ctx.setArgs("DEFINE name value".split(" "));
            assertThrows(ValueOfVariableMustBeNumericException.class, () -> defineCommand.execute(ctx));
        }

        @Test
        @Name("Name Must Start With Letter Test")
        public void shouldThrowVarNameMustStartWithLetter() {
            ctx.setArgs("DEFINE 1name 123".split(" "));
            assertThrows(VariableNameMustStartWithLetterException.class, () -> defineCommand.execute(ctx));
        }
    }

}