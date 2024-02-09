package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;

public class DivisionCommand implements Command{
    @Override
    public void execute(Context ctx) {
        ctx.setItem(ctx.getItem() / ctx.getItem());
    }
}
