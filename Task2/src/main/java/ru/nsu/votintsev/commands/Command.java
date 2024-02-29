package ru.nsu.votintsev.commands;

import ru.nsu.votintsev.Context;
import ru.nsu.votintsev.exceptions.CalculatorException;

public interface Command {
    void execute(Context ctx) throws CalculatorException;
}
