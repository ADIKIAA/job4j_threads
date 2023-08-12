package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() {
        return getContentWithPredicate((s) -> true);
    }

    public String getContentWithoutUnicode() {
        return getContentWithPredicate((s) -> s < 0x80);
    }

    private String getContentWithPredicate(Predicate<Integer>  predicate) {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) != -1) {
                if (predicate.test(data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }

}
