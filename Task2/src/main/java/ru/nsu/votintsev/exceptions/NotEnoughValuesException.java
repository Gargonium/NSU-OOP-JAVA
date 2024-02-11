package ru.nsu.votintsev.exceptions;

public class NotEnoughValuesException extends Exception {
    @Override
    public String getMessage() {
        return "There are not enough values on the stack to perform this operation";
    }
}
