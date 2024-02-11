package ru.nsu.votintsev.exceptions;

public class VariableNameMustStartWithLetterException extends Exception {
    @Override
    public String getMessage() {
        return "Variable Name must Start with Letter";
    }
}
