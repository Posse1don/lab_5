package com.example;

public class EncryptedDocument extends DocumentDecorator {
    public EncryptedDocument(Document document) {
        super(document);
    }

    @Override
    public String getContent() {
        return "Encrypted(" + document.getContent() + ")";
    }
}
