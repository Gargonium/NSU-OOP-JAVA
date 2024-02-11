package ru.nsu.votintsev.exceptions;

public class WrongNumberOfArgumentsException extends Exception {
    @Override
    public String getMessage() {
        return "Wrong Number of Arguments";
    }
}
