package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;
import ru.nsu.votintsev.exceptions.WrongNumberOfArgumentsException;

public class PrintCommand implements Command {
    @Override
    public void execute(Context ctx) throws WrongNumberOfArgumentsException {
        if (ctx.getNumberOfArgs() != 0) {
            throw new WrongNumberOfArgumentsException();
        }
        System.out.println(ctx.getItemSafe());
    }
}
