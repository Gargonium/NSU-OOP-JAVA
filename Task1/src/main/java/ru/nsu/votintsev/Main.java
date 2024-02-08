package ru.nsu.votintsev;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        for (String fileIn : args) {
            String fileOut = fileIn + "Out.csv";
            ParsersLinker pl = new ParsersLinker(fileIn, fileOut);
            try {
                pl.txtToCsv();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
