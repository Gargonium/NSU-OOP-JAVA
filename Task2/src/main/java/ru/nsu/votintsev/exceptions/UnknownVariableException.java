package ru.nsu.votintsev.exceptions;

public class UnknownVariableException extends CalculatorException {
    @Override
    public String getMessage() {
        return "Unknown Variable";
    }

    public UnknownVariableException() {
    }
}
