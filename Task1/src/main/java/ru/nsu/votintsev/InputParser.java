package ru.nsu.votintsev;

import java.io.*;
import java.util.*;

public class InputParser {
    private final String input_file_name;
    private final Map<String, Integer> freq_table;
    private int table_size = 0;

    public InputParser(String file_name) {
        freq_table = new HashMap<>();
        input_file_name = file_name;
    }

    // Считывает строку из входного файла и разбивая по словам, добавляет в Map
    public Map<String, Integer> readString() throws Exception {
        try (FileInputStream fin = new FileInputStream(input_file_name)) {
            BufferedInputStream bin = new BufferedInputStream(fin);
            try (Scanner ifs = new Scanner(bin)){
                while (ifs.hasNext()) {
                    String y = ifs.nextLine();
                    String[] words = y.split("(?U)\\W+");
                    addToTable(words);
                }
            }
        } catch (FileNotFoundException e) {
            throw new Exception("Error! File " + input_file_name + " not found!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return freq_table;
    }

    public int getTable_size() {
        return table_size;
    }

    // Распределяет массив слов в Map, параллельно ведя подсчет
    public void addToTable(String[] words) {
        for (String word : words) {
            word = word.toLowerCase();
            if (word.isEmpty()) continue;

            if (!freq_table.containsKey(word)) {
                freq_table.put(word, 1);
            } else {
                freq_table.put(word, freq_table.get(word) + 1);
            }
            table_size++;
        }
    }
}


