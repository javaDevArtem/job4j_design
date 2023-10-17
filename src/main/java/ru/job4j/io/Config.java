package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Config {

    private final String path;
    private final Map<String, String> values = new HashMap<>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        try (BufferedReader in = new BufferedReader(new FileReader(this.path))) {
            in.lines()
                    .filter(line -> line.contains("="))
                    .map(line -> line.split("=", 2))
                    .map(arr -> {
                        if (arr.length == 2 && Arrays.stream(arr).noneMatch(String::isBlank)) {
                            return arr;
                        } else {
                            throw new IllegalArgumentException("Incorrect data");
                        }
                    })
                    .forEach(arr -> values.put(arr[0], arr[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String value(String key) {
        return values.get(key);
    }

    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static void main(String[] args) {
        Config config = new Config("data/app.properties");
        config.load();
    }
}