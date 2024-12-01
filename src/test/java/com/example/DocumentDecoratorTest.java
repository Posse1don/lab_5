package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DocumentDecoratorTest {
    @Test
    public void testDecorators() throws Exception {
        Document doc = new TextDocument("Sample content.");

        Document encrypted = new EncryptedDocument(doc);
        String encryptedContent = encrypted.getContent();
        assertNotEquals("Sample content.", encryptedContent, "Зашифрований текст не повинен збігатися з оригіналом.");

        Document compressed = new CompressedDocument(encrypted);
        String compressedContent = compressed.getContent();
        assertTrue(compressedContent.startsWith("Compressed("), "Стиснутий текст має починатися зі слова 'Compressed'.");

        Document watermarked = new WatermarkedDocument(compressed, "Watermark");
        String finalContent = watermarked.getContent();
        assertTrue(finalContent.contains("[Watermark: Watermark]"), "Фінальний текст має містити водяний знак.");
    }
}

