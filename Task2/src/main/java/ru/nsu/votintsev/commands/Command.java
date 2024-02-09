package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;

public interface Command {
    void execute(Context ctx) throws Exception;
}
