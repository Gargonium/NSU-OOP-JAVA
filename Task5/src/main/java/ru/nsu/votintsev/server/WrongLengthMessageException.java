package ru.nsu.votintsev.server;

public class WrongLengthMessageException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Message got wrong length";
    }
}
