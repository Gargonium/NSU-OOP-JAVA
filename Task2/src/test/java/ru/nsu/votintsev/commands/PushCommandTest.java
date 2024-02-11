package ru.nsu.votintsev.commands;

import jdk.jfr.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nsu.votintsev.Context;
import ru.nsu.votintsev.exceptions.WrongNumberOfArgumentsException;

import static org.junit.jupiter.api.Assertions.*;

class PushCommandTest {
    PushCommand pushCommand;
    Context ctx;

    @BeforeEach
    public void setUp() {
        ctx = new Context();
        pushCommand = new PushCommand();
    }

    @Test
    @Name("Wrong Number of Arguments Test")
    public void shouldThrowWrongNumberOfArguments() {
        ctx.setArgs("PUSH 23 45".split(" "));
        assertThrows(WrongNumberOfArgumentsException.class, () -> pushCommand.execute(ctx));
    }

    @ParameterizedTest(name = "{index} - push number {0}")
    @ValueSource(strings = {"1.0", "0.0", "-1.0"})
    public void shouldWorkProperlyForNumbers(String value) {

        ctx.setArgs(("PUSH " + value).split(" "));

        assertDoesNotThrow(() -> pushCommand.execute(ctx));
        assertDoesNotThrow(() -> assertEquals(Double.parseDouble(value), ctx.getItemSafe()));
    }

    @ParameterizedTest(name = "{index} - push var {0}")
    @ValueSource(strings = {"first=1.0", "second=0.0", "third=-1.0"})
    public void shouldWorkProperlyForVariables(String varAndVal) {
        String name = varAndVal.split("=")[0];
        Double value = Double.parseDouble(varAndVal.split("=")[1]);
        ctx.setVarToMap(name, value);

        ctx.setArgs(("PUSH " + name).split(" "));

        assertDoesNotThrow(() -> pushCommand.execute(ctx));
        assertDoesNotThrow(() -> assertEquals(value, ctx.getItemSafe()));
    }

}