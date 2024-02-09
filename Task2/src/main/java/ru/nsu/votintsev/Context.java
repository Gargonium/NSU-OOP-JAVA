package ru.nsu.votintsev;

import java.util.HashMap;
import java.util.Stack;

public class Context {
    private final Stack<Double> stack = new Stack<>();
    private final HashMap<String, Double> map = new HashMap<>();

    private String firstArg;
    private String secondArg;

    // Getters
    public Double getVarFromMap(String key) {
        return map.get(key);
    }

    public Double getItemSafe() {
        return stack.peek();
    }
    public Double getItem() {
        return stack.pop();
    }

    public String getFirstArg() {
        return firstArg;
    }
    public String getSecondArg() {
        return secondArg;
    }

    // Setters
    public void setVarToMap(String key, Double value) {
        map.put(key, value);
    }

    public void setItem(Double item) {
        stack.push(item);
    }

    public void setArgs(String firstArg) {
        this.firstArg = firstArg;
    }
    public void setArgs(String firstArg, String secondArg) {
        this.firstArg = firstArg;
        this.secondArg = secondArg;
    }

    // Checkers
    public boolean isContains(String key) {
        return map.containsKey(key);
    }
}
