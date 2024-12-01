package com.example;

public class DraftState implements DocumentState {
    @Override
    public String getStateName() {
        return "Draft";
    }

    @Override
    public void moveToNext(DocumentLifecycle lifecycle) {
        lifecycle.setState(new ReviewState());
    }
}
