package ru.nsu.votintsev.factories;

import ru.nsu.votintsev.commands.Command;
import ru.nsu.votintsev.exceptions.UnknownCommandException;

import java.util.HashMap;
import java.util.Map;

public class CachingCommandFactory implements CommandFactory{

    private final CommandFactory basicCommandFactory;
    private final Map<String, Command> cache = new HashMap<>();

    public CachingCommandFactory(CommandFactory otherCommandFactory) {
        basicCommandFactory = otherCommandFactory;
    }

    @Override
    public Command createCommand(String input) throws UnknownCommandException {
        if (cache.containsKey(input)) {
            return cache.get(input);
        } else {
            Command command = basicCommandFactory.createCommand(input);
            cache.put(input, command);
            return command;
        }
    }
}
