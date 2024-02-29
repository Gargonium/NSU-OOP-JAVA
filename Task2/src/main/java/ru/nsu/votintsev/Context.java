package ru.nsu.votintsev;

import ru.nsu.votintsev.exceptions.NotEnoughValuesOnStackException;
import ru.nsu.votintsev.exceptions.UnknownVariableException;

import java.util.*;

public class Context {
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> map = new HashMap<>();

    private List<String> arguments = new ArrayList<>();

    // Getters
    public Double getVarFromMap(String key) throws UnknownVariableException {
        if (!this.isContains(key)) {
            throw new UnknownVariableException();
        }
        return map.get(key);
    }

    public Double getItemSafe() throws NotEnoughValuesOnStackException{
        try {
            return stack.peek();
        } catch (EmptyStackException e) {
            throw new NotEnoughValuesOnStackException();
        }
    }
    public Double getItem() throws NotEnoughValuesOnStackException{
        try {
            return stack.pop();
        } catch (EmptyStackException e) {
            throw new NotEnoughValuesOnStackException();
        }
    }

    public String getFirstArg() {
        return arguments.get(1);
    }
    public String getSecondArg() {
        return arguments.get(2);
    }
    public int getNumberOfArgs() {
        return arguments.size() - 1;
    }

    // Setters
    public void setVarToMap(String key, Double value) {
        map.put(key, value);
    }

    public void setItem(Double item) {
        stack.push(item);
    }

    public void setArgs(List<String> args) {
        arguments = args;
    }

    // Checkers
    public boolean isContains(String key) {
        return map.containsKey(key);
    }
}
