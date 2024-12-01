package com.example;

public class CompressedDocument extends DocumentDecorator {
    public CompressedDocument(Document document) {
        super(document);
    }

    @Override
    public String getContent() {
        return "Compressed(" + document.getContent() + ")";
    }
}
