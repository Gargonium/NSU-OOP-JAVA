package ru.nsu.votintsev;

import ru.nsu.votintsev.commands.Command;
import ru.nsu.votintsev.exceptions.CalculatorException;

import java.util.List;

public class StackCalculator {
    private final CommandFactory commandFactory = new CommandFactory();
    private final Context ctx = new Context();

    public void calculate(String input) throws CalculatorException {
        List<String> tokens = List.of(input.split(" "));
        Command command = commandFactory.createCommand(tokens.getFirst());
        ctx.setArgs(tokens);
        command.execute(ctx);
    }
}
