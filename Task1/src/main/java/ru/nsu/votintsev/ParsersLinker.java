package ru.nsu.votintsev;

import java.io.FileNotFoundException;
import java.util.Map;

public class ParsersLinker {
    private final String inFile;
    private final String outFile;

    public ParsersLinker(String inputFileName, String outputFileName) {
        inFile = inputFileName;
        outFile = outputFileName;
    }

    public void txtToCsv() throws FileNotFoundException  {
        InputParser inp = new InputParser(inFile);
        Map<String, Integer> freq_table = inp.readString();
        OutputParser oup = new OutputParser(outFile, freq_table);
        oup.setTable_size(inp.getTable_size());
        oup.writeLine();
    }

}
