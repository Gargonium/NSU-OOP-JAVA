package ru.nsu.votintsev;

import java.io.IOException;
import java.util.Map;

public class ParsersLinker {
    private final String inFile;
    private final String outFile;

    public ParsersLinker(String inputFileName, String outputFileName) {
        inFile = inputFileName;
        outFile = outputFileName;
    }

    public void txtToCsv() throws IOException {
        InputParser inp = new InputParser(inFile);
        Map<String, Integer> freqTable = inp.readString();
        OutputParser oup = new OutputParser(outFile, freqTable);
        oup.setTableSize(inp.getTableSize());
        oup.writeLine();
    }

}
