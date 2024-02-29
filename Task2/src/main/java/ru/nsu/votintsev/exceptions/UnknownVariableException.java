package ru.nsu.votintsev.exceptions;

public class UnknownVariableException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Unknown Variable";
    }
}
