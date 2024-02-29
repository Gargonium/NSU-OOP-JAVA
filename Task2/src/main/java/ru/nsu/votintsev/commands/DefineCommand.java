package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;
import ru.nsu.votintsev.exceptions.ValueOfVariableMustBeNumericException;
import ru.nsu.votintsev.exceptions.VariableNameMustStartWithLetterException;
import ru.nsu.votintsev.exceptions.WrongNumberOfArgumentsException;

public class DefineCommand implements Command {

    @Override
    public void execute(Context ctx) throws
            WrongNumberOfArgumentsException,
            ValueOfVariableMustBeNumericException,
            VariableNameMustStartWithLetterException
    {
        if (ctx.getNumberOfArgs() != 2) {
            throw new WrongNumberOfArgumentsException();
        }
        String name = ctx.getFirstArg();
        double value;
        if (Character.isLetter(name.charAt(0))) {
            try {
                value = Double.parseDouble(ctx.getSecondArg());
            } catch (NumberFormatException e) {
                throw new ValueOfVariableMustBeNumericException(e);
            }
        } else {
            throw new VariableNameMustStartWithLetterException();
        }

        ctx.setVarToMap(name, value);
    }
}