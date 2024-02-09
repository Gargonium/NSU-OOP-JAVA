package ru.nsu.votintsev;

import java.io.FileInputStream;
import java.util.Properties;

public class CommandFactory {
    public Object createCommand(String input, Context ctx) throws Exception {
        String[] tokens = input.split(" ");
        switch (tokens.length) {
            case 2: ctx.setArgs(tokens[1]); break;
            case 3: ctx.setArgs(tokens[1], tokens[2]); break;
            default: break;
        }

        Properties prop = new Properties();
        prop.load(new FileInputStream("config.ini"));
        return Class.forName(prop.getProperty(tokens[0]));
    }
}
