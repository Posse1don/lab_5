package com.example;

public class ReviewState implements DocumentState {
    @Override
    public String getStateName() {
        return "Review";
    }

    @Override
    public void moveToNext(DocumentLifecycle lifecycle) {
        lifecycle.setState(new ApprovedState());
    }
}
