package ru.nsu.votintsev;

import ru.nsu.votintsev.commands.Command;

public class StackCalculator {
    private final CommandFactory commandFactory = new CommandFactory();
    private final Context ctx = new Context();

    public void calculate(String input) throws Exception {
        String[] tokens = input.split(" ");
        Command command = commandFactory.createCommand(tokens[0]);
        ctx.setArgs(tokens);
        command.execute(ctx);
    }
}
