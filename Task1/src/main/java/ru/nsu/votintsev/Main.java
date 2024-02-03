package ru.nsu.votintsev;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileIn = args[0];
        String fileOut = args[0].substring(0, args[0].length()-4) + "Out.csv";
        ParsersLinker pl = new ParsersLinker(fileIn, fileOut);
        pl.txtToCsv();
    }
}
