package ru.nsu.votintsev;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class CommandReader {
    StackCalculator stackCalculator = new StackCalculator();

    public CommandReader() throws IOException {
    }

    public void readCommands(String[] args) throws IOException {
        if (args.length == 0) {
            readFromConsole();
        } else {
            readFromFile(args[0]);
        }
    }

    private void readFromConsole() {
        try (Scanner s = new Scanner(System.in)) {
            read(s);
        }
    }

    private void readFromFile(String fileName) throws IOException {
        try (Scanner s = new Scanner(new BufferedInputStream(new FileInputStream(fileName)))) {
            read(s);
        }
    }

    private void read(Scanner s) {
        int lineCount = 0;
        String line;

        while(s.hasNext()) {
            line = s.nextLine();
            lineCount++;
            if (!line.isEmpty()) {
                try {
                    stackCalculator.calculate(line);
                } catch (Exception e) {
                    System.out.println("line " + lineCount + ": " + e.getMessage());
                }
            }
        }
    }
}
