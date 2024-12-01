package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileDocument implements Document {
    private final Path filePath;

    public FileDocument(String filePath) {
        this.filePath = Path.of(filePath);
    }

    @Override
    public String getContent() {
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Помилка читання з файлу: " + filePath, e);
        }
    }

    public void saveContent(String content) {
        try {
            Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Помилка запису до файлу: " + filePath, e);
        }
    }

    public String getFilePath() {
        return filePath.toString();
    }
}

