package ru.nsu.votintsev;

import ru.nsu.votintsev.commands.Command;

public interface CommandFactoryInterface {
    Command createCommand(String input);
}
