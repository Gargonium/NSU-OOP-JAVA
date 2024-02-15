package ru.nsu.votintsev;

public class Main {
    public static void main(String[] args) {
        try {
            CommandReader commandReader = new CommandReader();
            commandReader.readCommands(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
