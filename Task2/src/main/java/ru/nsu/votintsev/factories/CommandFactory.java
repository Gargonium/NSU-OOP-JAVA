package ru.nsu.votintsev.factories;

import ru.nsu.votintsev.commands.Command;

public interface CommandFactory {
    Command createCommand(String input);
}
