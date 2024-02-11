package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;

public class PopCommand implements Command {
    @Override
    public void execute(Context ctx) throws Exception {
        if (ctx.getNumberOfArgs() != 0) {
            throw new Exception("Wrong Number of Arguments");
        }
        ctx.getItem();
    }
}
