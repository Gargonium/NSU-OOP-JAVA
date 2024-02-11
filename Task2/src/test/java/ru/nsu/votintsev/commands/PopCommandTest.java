package ru.nsu.votintsev.commands;

import jdk.jfr.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.votintsev.Context;
import ru.nsu.votintsev.exceptions.WrongNumberOfArgumentsException;

import static org.junit.jupiter.api.Assertions.*;

class PopCommandTest {
    PopCommand popCommand;
    Context ctx;

    @BeforeEach
    public void setUp() {
        ctx = new Context();
        popCommand = new PopCommand();
    }

    @Test
    @Name("Wrong Number of Arguments Test")
    public void shouldThrowWrongNumberOfArguments() {
        ctx.setArgs("POP 23 45".split(" "));
        assertThrows(WrongNumberOfArgumentsException.class, () -> popCommand.execute(ctx));
    }
}