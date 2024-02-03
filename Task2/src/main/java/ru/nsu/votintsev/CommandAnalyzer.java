package ru.nsu.votintsev;

public class CommandAnalyzer {

    private final Calculator calculator = new Calculator();

    public void analyzeAndCalc(String line) throws Exception {
        String[] instructions = line.split(" ");
        switch (instructions[0].toLowerCase()) {
            case "push":
                if (Character.isLetter(instructions[1].charAt(0))) {
                    calculator.push(instructions[1]);
                } else {
                    calculator.push(Double.parseDouble(instructions[1]));
                }
                break;
            case "pop":
                calculator.pop();
                break;
            case "print":
                calculator.print();
                break;
            case "define":
                calculator.define(instructions[1], Double.parseDouble(instructions[2]));
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
}
