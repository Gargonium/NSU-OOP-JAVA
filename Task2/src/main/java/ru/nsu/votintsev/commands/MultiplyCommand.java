package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;
import ru.nsu.votintsev.exceptions.WrongNumberOfArgumentsException;

public class MultiplyCommand implements Command{
    @Override
    public void execute(Context ctx) throws Exception {
        if (ctx.getNumberOfArgs() != 0) {
            throw new WrongNumberOfArgumentsException();
        }
        ctx.setItem(ctx.getItem() * ctx.getItem());
    }
}
