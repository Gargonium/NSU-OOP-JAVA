package ru.nsu.votintsev;

import ru.nsu.votintsev.commands.Command;
import ru.nsu.votintsev.exceptions.CalculatorException;
import ru.nsu.votintsev.factories.CachingCommandFactory;
import ru.nsu.votintsev.factories.ClassLoadingCommandFactory;
import ru.nsu.votintsev.factories.CommandFactory;

import java.io.IOException;
import java.util.List;

public class StackCalculator {
    private final CommandFactory commandFactory = new CachingCommandFactory(new ClassLoadingCommandFactory());
    private final Context ctx = new Context();

    public StackCalculator() throws IOException {
    }

    public void calculate(String input) throws CalculatorException {
        List<String> tokens = List.of(input.split(" "));
        Command command = commandFactory.createCommand(tokens.getFirst());
        ctx.setArgs(tokens);
        command.execute(ctx);
    }
}
