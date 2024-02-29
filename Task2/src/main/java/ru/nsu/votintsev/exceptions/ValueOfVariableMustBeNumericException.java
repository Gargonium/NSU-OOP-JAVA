package ru.nsu.votintsev.exceptions;

public class ValueOfVariableMustBeNumericException extends CalculatorException {
    @Override
    public String getMessage() {
        return "Value of Variable must be Numeric";
    }
}
