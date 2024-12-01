package com.example;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class EncryptedDocument extends DocumentDecorator {
    private static final String ALGORITHM = "AES";
    private static final SecretKey secretKey;

    static {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(128); // Використовується 128-бітний ключ
            secretKey = keyGen.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Помилка генерації ключа шифрування", e);
        }
    }

    public EncryptedDocument(Document document) {
        super(document);
    }

    @Override
    public String getContent() {
        try {
            String originalContent = document.getContent();
            return encrypt(originalContent);
        } catch (Exception e) {
            throw new RuntimeException("Помилка шифрування", e);
        }
    }

    private String encrypt(String content) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(content.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedContent) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedContent);
        byte[] originalBytes = cipher.doFinal(decodedBytes);
        return new String(originalBytes);
    }
}
