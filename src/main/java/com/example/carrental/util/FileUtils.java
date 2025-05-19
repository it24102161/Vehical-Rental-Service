package com.example.carrental.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static synchronized void writeToFile(String filePath, String content, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try {
            if (Files.exists(Paths.get(filePath))) {
                lines = Files.readAllLines(Paths.get(filePath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static synchronized void removeFromFile(String filePath, String lineToRemove) {
        List<String> lines = readFromFile(filePath);
        lines.removeIf(line -> line.contains(lineToRemove));

        try {
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void updateInFile(String filePath, String oldContent, String newContent) {
        List<String> lines = readFromFile(filePath);
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(oldContent)) {
                lines.set(i, newContent);
                break;
            }
        }

        try {
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFileIfNotExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Added method to clear a file's contents
    public static synchronized void clearFile(String filePath) {
        try {
            new FileWriter(filePath, false).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Added method to write a list of strings to a file
    public static synchronized void writeLinesToFile(String filePath, List<String> lines, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Added method to find and replace multiple occurrences in a file
    public static synchronized void findAndReplaceInFile(String filePath, String searchString, String replacement) {
        List<String> lines = readFromFile(filePath);

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(searchString)) {
                lines.set(i, lines.get(i).replace(searchString, replacement));
            }
        }

        try {
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}