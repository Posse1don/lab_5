package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DocumentLifecycleTest {
    @Test
    public void testLifecycleStates() {
        DocumentLifecycle lifecycle = new DocumentLifecycle();

        assertEquals("Draft", lifecycle.getCurrentState());
        lifecycle.moveToNextState();
        assertEquals("Review", lifecycle.getCurrentState());
        lifecycle.moveToNextState();
        assertEquals("Approved", lifecycle.getCurrentState());
        lifecycle.moveToNextState();
        assertEquals("Archived", lifecycle.getCurrentState());

        Exception exception = assertThrows(IllegalStateException.class, lifecycle::moveToNextState);
        assertEquals("Document is already archived.", exception.getMessage());
    }
}
