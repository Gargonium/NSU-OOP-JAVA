package ru.nsu.votintsev;

public class CommandAnalyzer {

    private final Calculator calculator = new Calculator();

    public void analyze(String line) throws Exception {
        String[] instructions = line.split(" ");
        switch (instructions[0].toLowerCase()) {
            case "push":
                if (Character.isLetter(instructions[1].charAt(0))) {
                    calculator.push(instructions[1]);
                } else if (isNumeric(instructions[1])) {
                    calculator.push(Double.parseDouble(instructions[1]));
                } else {
                    throw new Exception("Unknown variable");
                }
                break;
            case "pop":
                calculator.pop();
                break;
            case "print":
                calculator.print();
                break;
            case "define":
                if (Character.isLetter(instructions[1].charAt(0))) {
                    try {
                        calculator.define(instructions[1], Double.parseDouble(instructions[2]));
                    } catch (NumberFormatException e) {
                        throw new Exception("Value of variable must be numeric");
                    }
                } else {
                    throw new Exception("Variable name must start with letter");
                }
                break;
            case "+":
                calculator.operation('+');
                break;
            case "-":
                calculator.operation('-');
                break;
            case "*":
                calculator.operation('*');
                break;
            case "/":
                calculator.operation('/');
                break;
            case "sqrt":
                calculator.operation('s');
                break;
            default:
                if (instructions[0].charAt(0) != '#') {
                    throw new Exception("Unknown Command");
                }
        }
    }

    private boolean isNumeric(String word) {
        try {
            Double.parseDouble(word);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
