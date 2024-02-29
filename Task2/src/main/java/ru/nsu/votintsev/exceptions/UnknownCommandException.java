package ru.nsu.votintsev.exceptions;

public class UnknownCommandException extends CalculatorException {
    @Override
    public String getMessage() {
        return "Unknown Command";
    }

    public UnknownCommandException(Throwable cause) {
        super(cause);
    }
}
