package ru.nsu.votintsev;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class OutputParser {
    private final String outputFileName;
    private Map<String, Integer> freqTable;
    private int tableSize;

    public OutputParser(String outputFileName, Map<String, Integer> freqTable) {
        this.outputFileName = outputFileName;
        sortMap(freqTable);
    }
    // Сортирует Map по значению по убыванию
    private void sortMap(Map<String, Integer> freqTable) {
        this.freqTable = freqTable.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
    }


    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    // Создает строку и выводит ее в файл
    public void writeLine() throws IOException {
        try (FileWriter fout = new FileWriter(outputFileName)) {
            fout.write("Слово, Частота, Частота(в %)\n");
            for (String key : freqTable.keySet()) {
                int freq = freqTable.get(key);

                double procentFreq = ((double) freq / tableSize) * 100;
                String formattedDouble = new DecimalFormat("#0.000").format(procentFreq);
                formattedDouble = "\"" + formattedDouble + "%\"";

                fout.append(key).append(", ").append(String.valueOf(freq)).append(", ").append(formattedDouble).append("\n");
            }
        }
    }
}