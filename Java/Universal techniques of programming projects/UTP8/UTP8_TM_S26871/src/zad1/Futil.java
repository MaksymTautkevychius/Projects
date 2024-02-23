package zad1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Collectors;

public class Futil {
    public static void processDir(String dirName, String resultFileName) {
        try {
            String result = Files.walk(Paths.get(dirName))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .map(Futil::readFile)
                    .collect(Collectors.joining());

            Files.write(Paths.get(resultFileName), result.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(Path path) {
        try {
            return new String(Files.readAllBytes(path), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
