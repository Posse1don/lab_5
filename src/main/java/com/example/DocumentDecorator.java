package com.example;

public abstract class DocumentDecorator implements Document {
    protected final Document document;

    public DocumentDecorator(Document document) {
        this.document = document;
    }
}
