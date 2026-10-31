package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SingerFileLoader {
    private static final String SEPARATOR = ";";
    private static final int FIELDS_COUNT = 4;

    public MyArrayList<Singer> load(Path path) throws IOException {
        MyArrayList<Singer> result = new MyArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1 || line.isBlank()) {
                    continue;
                }
                try {
                    result.add(parseLine(line));
                } catch (IllegalArgumentException e) {
                    System.out.println("Строка " + lineNumber + " пропущена: " + e.getMessage());
                }
            }
        }
        return result;
    }

    private Singer parseLine(String line) {
        String[] parts = line.split(SEPARATOR);
        if (parts.length != FIELDS_COUNT) {
            throw new IllegalArgumentException(
                    "ожидалось " + FIELDS_COUNT + " поля, найдено " + parts.length);
        }
        int debutYear = parseNumber(parts[2], "год дебюта");
        int albums = parseNumber(parts[3], "число альбомов");
        return new Singer(parts[0], parts[1], debutYear, albums);
    }

    private int parseNumber(String value, String fieldName) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    fieldName + " должен быть целым числом, а не \"" + value.trim() + "\"");
        }
    }
}
