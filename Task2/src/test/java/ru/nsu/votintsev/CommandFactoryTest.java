package ru.nsu.votintsev;

import jdk.jfr.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.votintsev.exceptions.UnknownCommandException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest {
    CommandFactory commandFactory;

    @BeforeEach
    public void setUp() throws IOException {
       commandFactory = new CommandFactory();
    }

    @Test
    @Name("Unknown Command Test")
    public void shouldThrowUnknownCommandException() {
        assertThrows(UnknownCommandException.class, () -> commandFactory.createCommand("NaC"));
    }

    @Test
    @Name("Creating of command")
    public void shouldReturnRightCommand() {
        assertDoesNotThrow(() ->
                assertEquals(ru.nsu.votintsev.commands.PushCommand.class,
                        commandFactory.createCommand("PUSH").getClass()));
        assertDoesNotThrow(() ->
                assertEquals(ru.nsu.votintsev.commands.PopCommand.class,
                        commandFactory.createCommand("POP").getClass()));
        assertDoesNotThrow(() ->
                assertEquals(ru.nsu.votintsev.commands.PlusCommand.class,
                        commandFactory.createCommand("+").getClass()));
        assertDoesNotThrow(() ->
                assertEquals(ru.nsu.votintsev.commands.CommentCommand.class,
                        commandFactory.createCommand("#").getClass()));

    }

}