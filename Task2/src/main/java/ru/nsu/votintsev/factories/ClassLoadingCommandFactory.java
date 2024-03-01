package ru.nsu.votintsev.factories;

import ru.nsu.votintsev.commands.Command;
import ru.nsu.votintsev.exceptions.UnknownCommandException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ClassLoadingCommandFactory implements CommandFactory {
    private final Properties prop = new Properties();

    public ClassLoadingCommandFactory() throws IOException {
        try (BufferedInputStream file = new BufferedInputStream(Objects.requireNonNull(this.getClass().getResourceAsStream("/config.ini")))) {
            prop.load(file);
        }
    }

    @Override
    public Command createCommand(String input) throws UnknownCommandException {
        try {
            String commandName = prop.getProperty(input);
            return (Command) Class.forName(commandName).newInstance();
        } catch (Exception e) {
            throw new UnknownCommandException(e);
        }
    }
}
