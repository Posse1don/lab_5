package com.example;

public class Main {
    public static void main(String[] args) {
        Document doc = new TextDocument("This is a new document.");

        Document encrypted = new EncryptedDocument(doc);
        Document compressed = new CompressedDocument(encrypted);
        Document watermarked = new WatermarkedDocument(compressed, "Confidential");

        System.out.println("Final Content: " + watermarked.getContent());

        DocumentLifecycle lifecycle = new DocumentLifecycle();
        System.out.println("Initial State: " + lifecycle.getCurrentState());

        lifecycle.moveToNextState();
        System.out.println("State after review: " + lifecycle.getCurrentState());

        lifecycle.moveToNextState();
        System.out.println("State after approval: " + lifecycle.getCurrentState());

        lifecycle.moveToNextState();
        System.out.println("State after archival: " + lifecycle.getCurrentState());
    }
}
