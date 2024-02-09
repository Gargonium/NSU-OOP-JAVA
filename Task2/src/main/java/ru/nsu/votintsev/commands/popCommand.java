package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;

public class popCommand implements Command {
    @Override
    public void execute(Context ctx) {
        ctx.getItem();
    }
}
