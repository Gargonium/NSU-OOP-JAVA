package ru.nsu.votintsev;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static java.lang.Math.sqrt;

public class Calculator {
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> vars = new HashMap<>();

    public void pop() {
        stack.pop();
    }

    public void push(double item) {
        stack.push(item);
    }

    public void push(String item) throws Exception {
        if (vars.containsKey(item)) {
            stack.push(vars.get(item));
        } else {
            throw new Exception("Unknown variable");
        }
    }

    public void print() {
        System.out.println(stack.peek());
    }

    public void define(String variable, double value) {
        vars.put(variable, value);
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
