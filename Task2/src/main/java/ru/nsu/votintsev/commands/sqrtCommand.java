package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;

public class sqrtCommand implements Command{
    @Override
    public void execute(Context ctx) {
        ctx.setItem(Math.sqrt(ctx.getItem()));
    }
}
