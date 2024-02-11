package ru.nsu.votintsev.commands;

import jdk.jfr.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nsu.votintsev.Context;
import ru.nsu.votintsev.exceptions.WrongNumberOfArgumentsException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PrintCommandTest {
    PrintCommand printCommand;
    Context ctx;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        ctx = new Context();
        printCommand = new PrintCommand();
    }

    @Test
    @Name("Wrong Number of Arguments Test")
    public void shouldThrowWrongNumberOfArguments() {
        ctx.setArgs("PRINT 23 45".split(" "));
        assertThrows(WrongNumberOfArgumentsException.class, () -> printCommand.execute(ctx));
    }

    @ParameterizedTest(name = "{index} - Print {0}")
    @ValueSource(strings = {"1.0", "0.0", "-1.0"})
    public void shouldWorkProperly(String value) {
        System.setOut(new PrintStream(outputStreamCaptor));
        ctx.setArgs("PRINT".split(" "));

        ctx.setItem(Double.parseDouble(value));

        assertDoesNotThrow(() -> printCommand.execute(ctx));
        assertEquals(value, outputStreamCaptor.toString().trim());

        System.setOut(standardOut);
    }
}