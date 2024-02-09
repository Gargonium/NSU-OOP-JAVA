package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;

public class PopCommand implements Command {
    @Override
    public void execute(Context ctx) {
        ctx.getItem();
    }
}
