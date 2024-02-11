package ru.nsu.votintsev.exceptions;

public class UnknownVariableException extends Exception {
    @Override
    public String getMessage() {
        return "Unknown Variable";
    }
}
