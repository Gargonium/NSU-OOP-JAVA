package ru.nsu.votintsev.exceptions;

public class ValueOfVariableMustBeNumericException extends CalculatorException {
    public ValueOfVariableMustBeNumericException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "Value of Variable must be Numeric";
    }
}
