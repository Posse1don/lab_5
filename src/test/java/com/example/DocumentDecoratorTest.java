package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DocumentDecoratorTest {
    @Test
    public void testDecorators() {
        Document doc = new TextDocument("Sample content.");
        Document encrypted = new EncryptedDocument(doc);
        Document compressed = new CompressedDocument(encrypted);
        Document watermarked = new WatermarkedDocument(compressed, "Watermark");

        assertEquals("Compressed(Encrypted(Sample content.)) [Watermark: Watermark]", watermarked.getContent());
    }
}
