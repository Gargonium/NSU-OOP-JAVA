package ru.nsu.votintsev.exceptions;

public class UnknownCommandException extends Exception {
    @Override
    public String getMessage() {
        return "Unknown Command";
    }
}
