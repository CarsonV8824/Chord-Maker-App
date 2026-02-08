package com.example.data;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.*;

//reads zip files and prints their contents to the console

public class ZipReader {

    public static Iterable<ZipEntry> readZipEntries(String zipPath) throws IOException {
        return () -> new java.util.Iterator<ZipEntry>() {
            private ZipInputStream zis;
            private ZipEntry nextEntry;

            {
                try {
                    zis = new ZipInputStream(new FileInputStream(zipPath));
                    nextEntry = zis.getNextEntry();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public boolean hasNext() {
                return nextEntry != null;
            }

            @Override
            public ZipEntry next() {
                ZipEntry current = nextEntry;
                try {
                    nextEntry = zis.getNextEntry();
                    if (nextEntry == null) {
                        zis.close();
                    }
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
                return current;
            }
        };
    }

    public static void printZipLines(String zipPath) throws IOException {
        try (ZipFile zipFile = new ZipFile(zipPath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }

                System.out.println("Entry: " + entry.getName());

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(zipFile.getInputStream(entry), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }
        }
    }

    public static String readZipAsString(String zipPath) throws IOException {
    try (ZipFile zipFile = new ZipFile(zipPath)) {
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                continue;
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(zipFile.getInputStream(entry), StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                return sb.toString();
            }
        }
    }

    throw new FileNotFoundException("No file entries found in zip: " + zipPath);
}

    public static void main(String[] args) {
        String zipPath = args.length > 0 ? args[0] : "complex_chords.zip";
        try {
            //printZipLines(zipPath);
            String content = readZipAsString(zipPath);
            System.out.println("Full content of first file in zip:\n" + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}