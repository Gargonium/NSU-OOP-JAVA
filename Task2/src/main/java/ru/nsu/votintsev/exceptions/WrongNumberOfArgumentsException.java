package ru.nsu.votintsev.exceptions;

public class WrongNumberOfArgumentsException extends CalculatorException {
    public WrongNumberOfArgumentsException(Throwable cause) {
        super(cause);
    }

    public WrongNumberOfArgumentsException() {
    }

    @Override
    public String getMessage() {
        return "Wrong Number of Arguments";
    }
}
