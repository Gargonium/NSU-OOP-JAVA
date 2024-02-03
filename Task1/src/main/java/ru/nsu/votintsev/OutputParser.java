package ru.nsu.votintsev;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class OutputParser {
    private final String output_file_name;
    private Map<String, Integer> freq_table;
    private int table_size;

    public OutputParser(String outputFileName, Map<String, Integer> freqTable) {
        output_file_name = outputFileName;
        sortMap(freqTable);
    }
    // Сортирует Map по значению по убыванию
    private void sortMap(Map<String, Integer> freqTable) {
        freq_table = freqTable.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
    }


    public void setTable_size(int tableSize) {
        table_size = tableSize;
    }

    // Создает строку и выводит ее в файл
    public void writeLine() throws IOException {
        FileWriter fout = new FileWriter(output_file_name);
        fout.write("Слово, Частота, Частота(в %)\n");
        for (String key : freq_table.keySet()) {
            int freq = freq_table.get(key);

            double procent_freq = ((double) freq /table_size) * 100;
            String formatted_double = new DecimalFormat("#0.000").format(procent_freq);
            formatted_double = "\"" + formatted_double + "%\"";

            fout.append(key).append(", ").append(String.valueOf(freq)).append(", ").append(formatted_double).append("\n");
        }
        fout.close();
    }
}