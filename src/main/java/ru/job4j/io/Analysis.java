package ru.job4j.io;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analysis {
    public void unavailable(String source, String target) {
        try (BufferedReader in = new BufferedReader(new FileReader(source));
             BufferedWriter out = new BufferedWriter(new FileWriter(target))) {
            Pattern p = Pattern.compile("200|300");
            String startTime = "";
            String endTime = "";
            String line = in.readLine();
            while (line != null) {
                Matcher m = p.matcher(line);
                boolean isAvailable = m.find();
                if (!isAvailable) {
                    String[] dataArray = line.split(" ");
                    startTime = startTime.isBlank() ? dataArray[1] : startTime;
                }
                if (!startTime.isBlank() && isAvailable) {
                    String[] dataArray = line.split(" ");
                    endTime = dataArray[1];
                }
                if (!startTime.isBlank() && !endTime.isBlank()) {
                    out.write(startTime + ";" + endTime + ";" + "\n");
                    startTime = "";
                    endTime = "";
                }
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");
    }
}