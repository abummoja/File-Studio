/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filestudio;

/**
 *
 * @author Admin
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordPredictor {
    
    public WordPredictor(){
        
    }

    public static String findMostRepeatedWord(String directoryPath) {
        Map<String, Integer> wordCountMap = new HashMap<>();

        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String fileExtension = getFileExtension(fileName);

                    // Exclude file extensions
                    if (fileExtension != null && !fileExtension.isEmpty()) {
                        continue;
                    }

                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] words = line.split("\\s+");

                            for (String word : words) {
                                // Remove non-alphabetic characters from the word
                                word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();

                                // Update word count in the map
                                wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Find the word with the highest count
        int maxCount = 0;
        String mostRepeatedWord = null;

        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostRepeatedWord = entry.getKey();
            }
        }

        return mostRepeatedWord;
    }

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}