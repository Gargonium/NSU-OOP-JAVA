package ru.nsu.votintsev.exceptions;

public class NotEnoughValuesOnStackException extends CalculatorException {
    @Override
    public String getMessage() {
        return "There are not enough values on the stack to perform this operation";
    }

    public NotEnoughValuesOnStackException(Throwable cause) {
        super(cause);
    }
}
