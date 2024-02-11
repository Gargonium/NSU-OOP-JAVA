package ru.nsu.votintsev;

import ru.nsu.votintsev.exceptions.NotEnoughValuesOnStackException;
import ru.nsu.votintsev.exceptions.UnknownVariableException;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Context {
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> map = new HashMap<>();

    private String[] arguments;

    // Getters
    public Double getVarFromMap(String key) throws UnknownVariableException {
        if (!this.isContains(key)) {
            throw new UnknownVariableException();
        }
        return map.get(key);
    }

    public Double getItemSafe() throws Exception{
        try {
            return stack.peek();
        } catch (EmptyStackException e) {
            throw new NotEnoughValuesOnStackException();
        }
    }
    public Double getItem() throws Exception{
        try {
            return stack.pop();
        } catch (EmptyStackException e) {
            throw new NotEnoughValuesOnStackException();
        }
    }

    public String getFirstArg() {
        return arguments[1];
    }
    public String getSecondArg() {
        return arguments[2];
    }
    public int getNumberOfArgs() {
        return arguments.length - 1;
    }

    // Setters
    public void setVarToMap(String key, Double value) {
        map.put(key, value);
    }

    public void setItem(Double item) {
        stack.push(item);
    }

    public void setArgs(String[] args) {
        arguments = args;
    }

    // Checkers
    public boolean isContains(String key) {
        return map.containsKey(key);
    }
}
