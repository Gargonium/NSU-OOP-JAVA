package ru.nsu.votintsev;

import ru.nsu.votintsev.commands.Command;

public class StackCalculator {
    private final CommandFactory commandFactory = new CommandFactory();
    Context ctx = new Context();

    public void calculate(String input) throws Exception {
        Command command = (Command) commandFactory.createCommand(input, ctx);
        command.execute(ctx);
    }
}
