package ru.nsu.votintsev;

public class Main {
    public static void main(String[] args) {
        for (String fileIn : args) {
            String fileOut = fileIn.substring(0, fileIn.length() - 4) + "Out.csv";
            ParsersLinker pl = new ParsersLinker(fileIn, fileOut);
            pl.txtToCsv();
        }
    }
}
