package com.example;

public class ApprovedState implements DocumentState {
    @Override
    public String getStateName() {
        return "Approved";
    }

    @Override
    public void moveToNext(DocumentLifecycle lifecycle) {
        lifecycle.setState(new ArchivedState());
    }
}