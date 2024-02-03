package ru.nsu.votintsev;

public class Main {
    public static void main(String[] args) {
        CommandReader commandReader = new CommandReader();
        commandReader.readCommands(args);
    }
}
