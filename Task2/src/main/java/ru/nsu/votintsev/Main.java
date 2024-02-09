package ru.nsu.votintsev;

public class Main {
    public static void main(String[] args) {
        CommandReader commandReader = new CommandReader();
        try {
            commandReader.readCommands(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
