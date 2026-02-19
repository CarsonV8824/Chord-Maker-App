package com.example.data;
import com.example.data.ZipReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

//from https://rosettacode.org/wiki/Markov_chain_text_generator#

public class MarkovChain {
    private static Random r = new Random();

    private static String markov(String fileContent, int keySize, int outputSize) throws IOException {
        if (keySize < 1) throw new IllegalArgumentException("Key size can't be less than 1");
        String[] words = fileContent.trim().split(" ");
        if (outputSize < keySize || outputSize >= words.length) {
            throw new IllegalArgumentException("Output size is out of range");
        }
        Map<String, List<String>> dict = new HashMap<>();

        for (int i = 0; i < (words.length - keySize); ++i) {
            StringBuilder key = new StringBuilder(words[i]);
            for (int j = i + 1; j < i + keySize; ++j) {
                key.append(' ').append(words[j]);
            }
            String value = (i + keySize < words.length) ? words[i + keySize] : "";
            if (!dict.containsKey(key.toString())) {
                ArrayList<String> list = new ArrayList<>();
                list.add(value);
                dict.put(key.toString(), list);
            } else {
                dict.get(key.toString()).add(value);
            }
        }

        int n = 0;
        int rn = r.nextInt(dict.size());
        String prefix = (String) dict.keySet().toArray()[rn];
        List<String> output = new ArrayList<>(Arrays.asList(prefix.split(" ")));

        while (true) {
            List<String> suffix = dict.get(prefix);
            if (suffix == null || suffix.size() == 1) {
                if (suffix == null || Objects.equals(suffix.get(0), "")) return output.stream().reduce("", (a, b) -> a + " " + b);
                output.add(suffix.get(0));
            } else {
                rn = r.nextInt(suffix.size());
                output.add(suffix.get(rn));
            }
            if (output.size() >= outputSize) return output.stream().limit(outputSize).reduce("", (a, b) -> a + " " + b);
            n++;
            prefix = output.stream().skip(n).limit(keySize).reduce("", (a, b) -> a + " " + b).trim();
        }
    }

    public static String generateJazzChords(int outputSize) throws IOException {
        String file = ZipReader.readZipAsString("complex_chords.zip");
        return markov(file, 3, outputSize);
    }

    public static String generatePopularChords(int outputSize) throws IOException {
        String file = ZipReader.readZipAsString("chords.zip");
        return markov(file, 3, outputSize);
    }

    public static void main(String[] args) throws IOException {
        String file = ZipReader.readZipAsString("complex_chords.zip");
        System.out.println(markov(file, 3, 200));
    }
}