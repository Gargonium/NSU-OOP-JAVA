package ru.nsu.votintsev.exceptions;

public class VariableNameMustStartWithLetterException extends CalculatorException {

    public VariableNameMustStartWithLetterException() {
    }

    @Override
    public String getMessage() {
        return "Variable Name must Start with Letter";
    }
}
