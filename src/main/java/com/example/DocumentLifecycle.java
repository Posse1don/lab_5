package com.example;

public class DocumentLifecycle {
    private DocumentState currentState;

    public DocumentLifecycle() {
        this.currentState = new DraftState();
    }

    public void setState(DocumentState state) {
        this.currentState = state;
    }

    public void moveToNextState() {
        currentState.moveToNext(this);
    }

    public String getCurrentState() {
        return currentState.getStateName();
    }
}
