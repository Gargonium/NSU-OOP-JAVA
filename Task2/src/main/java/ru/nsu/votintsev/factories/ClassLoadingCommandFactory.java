package ru.nsu.votintsev.factories;

import ru.nsu.votintsev.commands.Command;
import ru.nsu.votintsev.exceptions.UnknownCommandException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ClassLoadingCommandFactory implements CommandFactory {
    private final Properties prop = new Properties();
    FileInputStream config;

    public ClassLoadingCommandFactory() throws UnknownCommandException {
        try (FileInputStream file = new FileInputStream("config.ini")) {
            config = file;
        } catch (IOException e) {
            throw new UnknownCommandException(e);
        }
    }

    @Override
    public Command createCommand(String input) throws UnknownCommandException {
        try {
            prop.load(config);
            String commandName = prop.getProperty(input);
            return (Command) Class.forName(commandName).newInstance();
        } catch (Exception e) {
            throw new UnknownCommandException(e);
        }
    }

    // Еще одна реализация фабрики, которая получают другую реализацию в конструктор. В первый раз она вызывает полученную фабрику, а далее, если эту же команду возвращает то что уже было сделано
    // Кэширующий прокси паттерн.
}
