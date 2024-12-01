package com.example;

public class ArchivedState implements DocumentState {
    @Override
    public String getStateName() {
        return "Archived";
    }

    @Override
    public void moveToNext(DocumentLifecycle lifecycle) {
        throw new IllegalStateException("Document is already archived.");
    }
}