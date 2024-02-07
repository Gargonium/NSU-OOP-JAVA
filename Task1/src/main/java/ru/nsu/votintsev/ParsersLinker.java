package ru.nsu.votintsev;

import java.util.Map;

public class ParsersLinker {
    private final String inFile;
    private final String outFile;

    public ParsersLinker(String inputFileName, String outputFileName) {
        inFile = inputFileName;
        outFile = outputFileName;
    }

    public void txtToCsv() {
        try {
            InputParser inp = new InputParser(inFile);
            Map<String, Integer> freq_table = inp.readString();
            OutputParser oup = new OutputParser(outFile, freq_table);
            oup.setTable_size(inp.getTable_size());
            oup.writeLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
