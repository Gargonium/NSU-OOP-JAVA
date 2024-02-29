package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;
import ru.nsu.votintsev.exceptions.WrongNumberOfArgumentsException;

public class SqrtCommand implements Command{
    @Override
    public void execute(Context ctx) throws WrongNumberOfArgumentsException {
        if (ctx.getNumberOfArgs() != 0) {
            throw new WrongNumberOfArgumentsException();
        }
        ctx.setItem(Math.sqrt(ctx.getItem()));
    }
}
