package ru.nsu.votintsev.exceptions;

public class WrongNumberOfArgumentsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Wrong Number of Arguments";
    }
}
