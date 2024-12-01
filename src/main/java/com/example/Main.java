package com.example;

import java.util.Scanner;

public class Main {
    private static Document currentDocument;
    private static DocumentLifecycle lifecycle;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        lifecycle = new DocumentLifecycle();
        boolean exit = false;

        while (!exit) {
            System.out.println("\nМеню:");
            System.out.println("1. Створити новий документ");
            System.out.println("2. Застосувати декоратор");
            System.out.println("3. Змінити стан документа");
            System.out.println("4. Показати вміст документа");
            System.out.println("5. Вийти");
            System.out.print("Виберіть опцію: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // очищення буфера

            switch (choice) {
                case 1:
                    createNewDocument(scanner);
                    break;
                case 2:
                    applyDecorator(scanner);
                    break;
                case 3:
                    changeDocumentState();
                    break;
                case 4:
                    showDocumentInfo();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Завершення роботи...");
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
        scanner.close();
    }

    private static void createNewDocument(Scanner scanner) {
        System.out.print("Введіть вміст нового документа: ");
        String content = scanner.nextLine();
        currentDocument = new TextDocument(content);
        lifecycle = new DocumentLifecycle();
        System.out.println("Документ створено.");
    }

    private static void applyDecorator(Scanner scanner) {
        if (currentDocument == null) {
            System.out.println("Спочатку створіть документ!");
            return;
        }

        System.out.println("Доступні декоратори:");
        System.out.println("1. Зашифрувати");
        System.out.println("2. Стиснути");
        System.out.println("3. Додати водяний знак");
        System.out.print("Виберіть декоратор: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                currentDocument = new EncryptedDocument(currentDocument);
                System.out.println("Документ зашифровано.");
                break;
            case 2:
                currentDocument = new CompressedDocument(currentDocument);
                System.out.println("Документ стиснуто.");
                break;
            case 3:
                System.out.print("Введіть текст водяного знаку: ");
                String watermark = scanner.nextLine();
                currentDocument = new WatermarkedDocument(currentDocument, watermark);
                System.out.println("Водяний знак додано.");
                break;
            default:
                System.out.println("Невірний вибір.");
        }
    }

    private static void changeDocumentState() {
        if (currentDocument == null) {
            System.out.println("Спочатку створіть документ!");
            return;
        }

        try {
            lifecycle.moveToNextState();
            System.out.println("Стан документа змінено на: " + lifecycle.getCurrentState());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void showDocumentInfo() {
        if (currentDocument == null) {
            System.out.println("Документ ще не створено.");
            return;
        }

        System.out.println("Поточний стан документа: " + lifecycle.getCurrentState());
        System.out.println("Вміст документа: " + currentDocument.getContent());
    }
}
