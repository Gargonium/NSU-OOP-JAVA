package ru.nsu.votintsev.exceptions;

public class VariableNameMustStartWithLetterException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Variable Name must Start with Letter";
    }
}
