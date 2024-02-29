package ru.nsu.votintsev.exceptions;

public class UnknownCommandException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Unknown Command";
    }
}
