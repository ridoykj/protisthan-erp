package com.itbd.protisthan.others;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReads {

    @Test
    public void test() {
        Path startPath = Paths.get("D:\\Movie");
        try (Stream<Path> stream = Files.walk(startPath)) {
//            stream.filter(Files::isRegularFile)
//                    .forEach(System.out::println);
            StringBuilder sb = new StringBuilder();
            stream.filter(Files::isRegularFile).toList().forEach(p -> sb.append(p).append("\n"));
            FileUtils.write(new File("J:\\D_Drive_file.txt"), sb.toString(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
