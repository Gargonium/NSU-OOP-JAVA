package ru.nsu.votintsev;

import ru.nsu.votintsev.commands.Command;

import java.io.FileInputStream;
import java.util.Properties;

public class CommandFactory {
    public Command createCommand(String input) throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("config.ini"));
        String commandName = prop.getProperty(input);
        if (commandName == null) {
            throw new Exception("Unknown Command");
        }
        return (Command) Class.forName(commandName).newInstance();
    }
}
