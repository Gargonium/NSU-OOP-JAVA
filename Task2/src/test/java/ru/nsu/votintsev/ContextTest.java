package ru.nsu.votintsev;

import jdk.jfr.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.nsu.votintsev.exceptions.NotEnoughValuesOnStackException;
import ru.nsu.votintsev.exceptions.UnknownVariableException;

import static org.junit.jupiter.api.Assertions.*;

class ContextTest {
    Context ctx;

    @BeforeEach
    void setUp() {
        ctx = new Context();
    }

    @Nested
    class ThrowExceptionTests {

        @Test
        @Name("Unknown Variable Test")
        public void shouldThrowUnknownVarException() {
            assertThrows(UnknownVariableException.class, () -> ctx.getVarFromMap("null"));
        }

        @Test
        @Name("There are no values on the stack Test")
        public void shouldThrowNothingToPeek() {
            assertThrows(NotEnoughValuesOnStackException.class, () -> ctx.getItemSafe());
            assertThrows(NotEnoughValuesOnStackException.class, () -> ctx.getItem());
        }
    }
}