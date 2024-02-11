package ru.nsu.votintsev.exceptions;

public class ValueOfVariableMustBeNumericException extends Exception {
    @Override
    public String getMessage() {
        return "Value of Variable must be Numeric";
    }
}
