package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogFilter {
    private final String file;

    public LogFilter(String file) {
        this.file = file;
    }

    public List<String> filter() {
        List<String> logs = new ArrayList<>();
        Pattern pattern = Pattern.compile("\s404\s");
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            logs = in.lines()
                    .filter(line -> pattern.matcher(line).find())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logs;
    }

    public void saveTo(String path) {
        var data = filter();
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(path)
                ))) {
            data.forEach(out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LogFilter logFilter = new LogFilter("data/log.txt");
        logFilter.filter().forEach(System.out::println);
        new LogFilter("data/log.txt").saveTo("data/404.txt");
    }
}