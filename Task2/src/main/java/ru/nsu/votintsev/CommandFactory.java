package ru.nsu.votintsev;

import ru.nsu.votintsev.commands.Command;
import ru.nsu.votintsev.exceptions.UnknownCommandException;

import java.io.FileInputStream;
import java.util.Properties;

public class CommandFactory implements CommandFactoryInterface {
    private final Properties prop = new Properties();

    public CommandFactory() {
    }

    @Override
    public Command createCommand(String input) throws UnknownCommandException {
        try {
            prop.load(new FileInputStream("config.ini"));
            String commandName = prop.getProperty(input);
            return (Command) Class.forName(commandName).newInstance();
        } catch (Exception e) {
            throw new UnknownCommandException();
        }
    }
}
