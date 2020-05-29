package com.kyriba.log.reducer.processor;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

@AllArgsConstructor(staticName = "of")
public class FileWriter {

    private Path outputPath;

    public void write(List<String> lines) throws IOException {
        Files.write(outputPath, lines, CREATE, APPEND, WRITE);
    }
}
