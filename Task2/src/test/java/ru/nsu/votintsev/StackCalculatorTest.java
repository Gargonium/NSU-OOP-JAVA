package ru.nsu.votintsev;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StackCalculatorTest {
    @Test
    public void twoPlusTwoShouldEqualFour() throws Exception {
        StackCalculator stackCalculator = new StackCalculator();
        stackCalculator.calculate("PUSH 2");
        stackCalculator.calculate("PUSH 2");
        stackCalculator.calculate("+");
        stackCalculator.calculate("PRINT");
        assertEquals(4, 2+2); // equals - pass
        assertNotEquals(4, 2+3); // don`t equals - pass
        assertTrue(true); // if true - pass
        assertFalse(false); // if false - pass
        assertNull(null); // if null - pass
        assertNotNull(stackCalculator); // if not null - pass
        assertThrows(Exception.class, () -> stackCalculator.calculate("PUSH ar")); // if throws exc - pass
    }
}