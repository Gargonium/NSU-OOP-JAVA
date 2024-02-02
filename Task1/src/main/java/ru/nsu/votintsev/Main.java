package ru.nsu.votintsev;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ParsersLinker pl = new ParsersLinker("file.txt", "fileOut.csv");
        pl.txtToCsv();
    }
}

