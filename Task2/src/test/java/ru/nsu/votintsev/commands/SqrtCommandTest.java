package ru.nsu.votintsev.commands;

import jdk.jfr.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nsu.votintsev.Context;
import ru.nsu.votintsev.exceptions.WrongNumberOfArgumentsException;

import static org.junit.jupiter.api.Assertions.*;

class SqrtCommandTest {
    SqrtCommand sqrtCommand;
    Context ctx;

    @BeforeEach
    public void setUp() {
        ctx = new Context();
        sqrtCommand = new SqrtCommand();
    }

    @Test
    @Name("Wrong Number of Arguments Test")
    public void shouldThrowWrongNumberOfArguments() {
        ctx.setArgs("SQRT 23 45".split(" "));
        assertThrows(WrongNumberOfArgumentsException.class, () -> sqrtCommand.execute(ctx));
    }

    @ParameterizedTest(name = "{index} - {0}")
    @ValueSource(strings = {"sqrt36.0=6.0", "sqrt16.0=4.0", "sqrt11.56=3.4"})
    public void shouldWorkProperly(String valuesAndResult) {
        Double what = Double.parseDouble(valuesAndResult.split("sqrt")[1].split("=")[0]);
        Double result = Double.parseDouble(valuesAndResult.split("=")[1]);

        ctx.setArgs("SQRT".split(" "));
        ctx.setItem(what);
        assertDoesNotThrow(() -> sqrtCommand.execute(ctx));
        assertDoesNotThrow(() -> assertEquals(result, ctx.getItemSafe()));
    }
}