package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;

public class DefineCommand implements Command {

    @Override
    public void execute(Context ctx) throws Exception {
        if (ctx.getNumberOfArgs() != 2) {
            throw new Exception("Wrong Number of Arguments");
        }
        String name = ctx.getFirstArg();
        double value;
        if (Character.isLetter(name.charAt(0))) {
            try {
                value = Double.parseDouble(ctx.getSecondArg());
            } catch (NumberFormatException e) {
                throw new Exception("Value of variable must be numeric");
            }
        } else {
            throw new Exception("Variable name must start with letter");
        }

        ctx.setVarToMap(name, value);
    }
}