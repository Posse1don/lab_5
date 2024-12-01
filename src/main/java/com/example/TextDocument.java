package com.example;

public class TextDocument implements Document {
    private final String content;

    public TextDocument(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }
}

