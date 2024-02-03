package ru.nsu.votintsev;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ParsersLinker pl = new ParsersLinker("file.txt", "fileOut.csv");
        pl.txtToCsv();
    }
}

