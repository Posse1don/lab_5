package com.example;

public interface DocumentState {
    String getStateName();
    void moveToNext(DocumentLifecycle lifecycle);
}
