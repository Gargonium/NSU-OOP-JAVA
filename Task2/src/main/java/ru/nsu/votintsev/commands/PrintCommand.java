package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;

public class PrintCommand implements Command {
    @Override
    public void execute(Context ctx) {
        System.out.println(ctx.getItemSafe());
    }
}
