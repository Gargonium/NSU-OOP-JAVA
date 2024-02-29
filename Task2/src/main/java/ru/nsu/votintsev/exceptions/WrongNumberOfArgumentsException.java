package ru.nsu.votintsev.exceptions;

public class WrongNumberOfArgumentsException extends CalculatorException {
    @Override
    public String getMessage() {
        return "Wrong Number of Arguments";
    }
}
