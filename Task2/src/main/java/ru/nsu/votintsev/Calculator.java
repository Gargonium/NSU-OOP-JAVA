package ru.nsu.votintsev;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static java.lang.Math.sqrt;

public class Calculator {
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> defs = new HashMap<>();

    public void pop() {
        stack.pop();
    }

    public void push(double item) {
        stack.push(item);
    }

    public void push(String item) {
        if (defs.containsKey(item)) {
            stack.push(defs.get(item));
        }
        // Need to add throw UnknownVarException
    }

    public void print() {
        System.out.println(stack.peek());
    }

    public void define(String variable, double value) {
        defs.put(variable, value);
    }

    public void operation(char op) {
        double upItem = stack.pop();
        switch (op) {
            case '+':
                stack.push(stack.pop() + upItem);
                break;
            case '-':
                stack.push(stack.pop() - upItem);
                break;
            case '*':
                stack.push(stack.pop() * upItem);
                break;
            case '/':
                stack.push(stack.pop() / upItem);
                break;
            case 's':
                stack.push(sqrt(upItem));
                break;
            default:
                break;
        }
    }


}
