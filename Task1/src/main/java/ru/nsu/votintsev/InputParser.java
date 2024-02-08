package ru.nsu.votintsev;

import java.io.*;
import java.util.*;

public class InputParser {
    private final String inputFileName;
    private final Map<String, Integer> freqTable;
    private int tableSize = 0;

    public InputParser(String fileName) {
        freqTable = new HashMap<>();
        inputFileName = fileName;
    }

    // Считывает строку из входного файла и разбивая по словам, добавляет в Map
    public Map<String, Integer> readString() throws IOException {
        try (FileInputStream fin = new FileInputStream(inputFileName)) {
            BufferedInputStream bin = new BufferedInputStream(fin);
            try (Scanner ifs = new Scanner(bin)) {
                while (ifs.hasNext()) {
                    String y = ifs.nextLine();
                    String[] words = y.split("(?U)\\W+");
                    addToTable(words);
                }
            }
        }
        return freqTable;
    }

    public int getTableSize() {
        return tableSize;
    }

    // Распределяет массив слов в Map, параллельно ведя подсчет
    public void addToTable(String[] words) {
        for (String word : words) {
            word = word.toLowerCase();
            if (word.isEmpty()) continue;

            if (!freqTable.containsKey(word)) {
                freqTable.put(word, 1);
            } else {
                freqTable.put(word, freqTable.get(word) + 1);
            }
            tableSize++;
        }
    }
}


