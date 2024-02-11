package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;
import ru.nsu.votintsev.exceptions.WrongNumberOfArgumentsException;

public class PushCommand implements Command{
    private Double value = null;
    private String key = null;

    @Override
    public void execute(Context ctx) throws Exception {
        if (ctx.getNumberOfArgs() != 1) {
            throw new WrongNumberOfArgumentsException();
        }
        try {
            value = Double.parseDouble(ctx.getFirstArg());
        } catch (NumberFormatException e) {
            this.key = ctx.getFirstArg();
        }

        ctx.setItem(value == null ? ctx.getVarFromMap(key) : value);
    }
}