package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;

public class defineCommand implements Command {
    @Override
    public void execute(Context ctx) {
        ctx.setVarToMap(ctx.getFirstArg(), Double.parseDouble(ctx.getSecondArg()));
    }
}

/*
case "define":
                if (Character.isLetter(instructions[1].charAt(0))) {
                    try {
//                        calculator.define(instructions[1], Double.parseDouble(instructions[2]));
                    } catch (NumberFormatException e) {
                        throw new Exception("Value of variable must be numeric");
                    }
                } else {
                    throw new Exception("Variable name must start with letter");
                }
                break;
 */