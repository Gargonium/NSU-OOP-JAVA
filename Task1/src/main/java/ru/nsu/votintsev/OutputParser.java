package ru.nsu.votintsev;

import java.util.*;

public class OutputParser {
    private final String output_file_name;
    private SortedMap<String, Integer> freq_table;
    private int table_size;

    public OutputParser(String outputFileName, Map<String, Integer> freqTable) {
        output_file_name = outputFileName;
        freq_table = new TreeMap<>(freqTable);
    }

    public void setTable_size(int tableSize) {
        table_size = tableSize;
    }

    public void writeLine() {
        System.out.println(freq_table);
    }
}