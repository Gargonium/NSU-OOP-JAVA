package ru.nsu.votintsev;

import ru.nsu.votintsev.commands.Command;
import ru.nsu.votintsev.exceptions.UnknownCommandException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CommandFactory {
    Properties prop = new Properties();

    public CommandFactory() throws IOException {
        prop.load(new FileInputStream("config.ini"));
    }

    public Command createCommand(String input) throws Exception {
        String commandName = prop.getProperty(input);
        if (commandName == null) {
            throw new UnknownCommandException();
        }
        return (Command) Class.forName(commandName).newInstance();
    }
}
