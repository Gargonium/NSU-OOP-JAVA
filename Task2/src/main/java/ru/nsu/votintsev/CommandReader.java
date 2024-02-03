package ru.nsu.votintsev;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class CommandReader {
    CommandAnalyzer commandAnalyzer = new CommandAnalyzer();

    public void readCommands(String[] args) {
        if (args.length == 0) {
            readFromConsole();
        } else {
            readFromFile(args[0]);
        }
    }

    private void readFromConsole() {
        try (Scanner s = new Scanner(System.in)) {
            String line = s.nextLine();
            while(!line.equalsIgnoreCase("exit")) {
                if (!line.isEmpty()) {
                    try {
                        commandAnalyzer.analyze(line);
                    } catch (Exception e) {
                        System.out.printf(e.getMessage());
                    }
                }
                line = s.nextLine();
            }
        }
    }

    private void readFromFile(String fileName) {
        try (Scanner s = new Scanner(new BufferedInputStream(new FileInputStream(fileName)))) {
            int lineCount = 0;
            String line;

            while(s.hasNext()) {
                line = s.nextLine();
                lineCount++;
                if (!line.isEmpty()) {
                    try {
                        commandAnalyzer.analyze(line);
                    } catch (Exception e) {
                        System.out.printf(e.getMessage() + "\nline: " + lineCount);
                        System.exit(1);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
