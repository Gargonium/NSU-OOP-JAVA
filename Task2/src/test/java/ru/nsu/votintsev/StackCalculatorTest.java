package ru.nsu.votintsev;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StackCalculatorTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private StackCalculator stackCalculator;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        stackCalculator = new StackCalculator();
    }

    @Test
    public void shouldWorkProperly() {
        assertDoesNotThrow(() -> stackCalculator.calculate("PUSH 4"));
        assertDoesNotThrow(() -> stackCalculator.calculate("PRINT"));
        assertDoesNotThrow(() -> assertEquals("4.0",
                outputStreamCaptor.toString().trim()));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}