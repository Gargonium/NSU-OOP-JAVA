package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;

public class pushCommand implements Command{
    private Double value = null;
    private String key = null;

    @Override
    public void execute(Context ctx) {
        try {
            value = Double.parseDouble(ctx.getFirstArg());
        } catch (NumberFormatException e) {
            this.key = ctx.getFirstArg();
        }
        ctx.setItem(value == null ? ctx.getVarFromMap(key) : value);
    }
}

/*
case "push":
                if (Character.isLetter(instructions[1].charAt(0))) {
                    //calculator.push(instructions[1]);
                } else if (isNumeric(instructions[1])) {
                    //calculator.push(Double.parseDouble(instructions[1]));
                } else {
                    throw new Exception("Unknown variable");
                }
                break;
public void push(String item) throws Exception {
        if (vars.containsKey(item)) {
            stack.push(vars.get(item));
        } else {
            throw new Exception("Unknown variable");
        }
    }
 */