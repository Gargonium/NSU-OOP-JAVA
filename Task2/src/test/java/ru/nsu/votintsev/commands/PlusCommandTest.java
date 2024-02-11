package ru.nsu.votintsev.commands;

import jdk.jfr.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nsu.votintsev.Context;
import ru.nsu.votintsev.exceptions.WrongNumberOfArgumentsException;

import static org.junit.jupiter.api.Assertions.*;

class PlusCommandTest {
    PlusCommand plusCommand;
    Context ctx;

    @BeforeEach
    public void setUp() {
        ctx = new Context();
        plusCommand = new PlusCommand();
    }

    @Test
    @Name("Wrong Number of Arguments Test")
    public void shouldThrowWrongNumberOfArguments() {
        ctx.setArgs("+ 23 45".split(" "));
        assertThrows(WrongNumberOfArgumentsException.class, () -> plusCommand.execute(ctx));
    }

    @ParameterizedTest(name = "{index} - {0}")
    @ValueSource(strings = {"12.0+6.0=18.0", "48.0+16.0=64.0", "2.0+3.5=5.5"})
    public void shouldWorkProperly(String valuesAndResult) {
        Double whereFrom = Double.parseDouble(valuesAndResult.split("\\+")[0]);
        Double what = Double.parseDouble(valuesAndResult.split("\\+")[1].split("=")[0]);
        Double result = Double.parseDouble(valuesAndResult.split("=")[1]);

        ctx.setArgs("+".split(" "));
        ctx.setItem(what);
        ctx.setItem(whereFrom);
        assertDoesNotThrow(() -> plusCommand.execute(ctx));
        assertDoesNotThrow(() -> assertEquals(result, ctx.getItemSafe()));
    }

}