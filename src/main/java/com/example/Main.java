package com.example;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String FILES_DIRECTORY = "files";
    private static FileDocument currentFileDocument;
    private static Document currentDocument;
    private static DocumentLifecycle currentLifecycle; // Унікальний стан для кожного документа

    public static void main(String[] args) {
        ensureFilesDirectoryExists();
        Scanner scanner = new Scanner(System.in);
        mainMenu(scanner);
    }

    private static void ensureFilesDirectoryExists() {
        File directory = new File(FILES_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    private static void mainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Головне меню ---");
            System.out.println("1. Обрати файл");
            System.out.println("2. Робота з обраним файлом");
            System.out.println("3. Вийти");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // очищення буфера

            switch (choice) {
                case 1 -> selectFile(scanner);
                case 2 -> fileOperationsMenu(scanner);
                case 3 -> {
                    System.out.println("Завершення роботи.");
                    return;
                }
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

    private static void selectFile(Scanner scanner) {
        File directory = new File(FILES_DIRECTORY);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("У папці 'files' немає доступних файлів.");
            return;
        }

        System.out.println("\n--- Доступні файли ---");
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i].getName());
        }
        System.out.print("Виберіть файл за номером: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > files.length) {
            System.out.println("Невірний вибір.");
            return;
        }

        currentFileDocument = new FileDocument(files[choice - 1].getPath());
        currentDocument = currentFileDocument;
        currentLifecycle = new DocumentLifecycle(); // Ініціалізуємо новий стан для нового документа
        System.out.println("Файл обрано: " + files[choice - 1].getName());
    }

    private static void fileOperationsMenu(Scanner scanner) {
        if (currentDocument == null) {
            System.out.println("Спочатку оберіть файл.");
            return;
        }

        while (true) {
            System.out.println("\n--- Операції з файлом ---");
            System.out.println("1. Переглянути вміст файлу");
            System.out.println("2. Застосувати декоратор");
            System.out.println("3. Змінити стан документа");
            System.out.println("4. Зберегти зміни у файл");
            System.out.println("5. Інформація про файл");
            System.out.println("6. Повернутися до головного меню");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showFileContent();
                case 2 -> applyDecorator(scanner);
                case 3 -> manageLifecycle(scanner);
                case 4 -> saveFileChanges();
                case 5 -> showFileInfo();
                case 6 -> {
                    return;
                }
                default -> System.out.println("Невірний вибір.");
            }
        }
    }


    private static void showFileContent() {
        System.out.println("\n--- Вміст файлу ---");
        System.out.println(currentDocument.getContent());
    }

    private static void applyDecorator(Scanner scanner) {
        System.out.println("\n--- Декоратори ---");
        System.out.println("1. Зашифрувати документ");
        System.out.println("2. Стиснути документ");
        System.out.println("3. Додати водяний знак");
        System.out.println("4. Дешифрувати документ");
        System.out.print("Ваш вибір: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> currentDocument = new EncryptedDocument(currentDocument);
            case 2 -> currentDocument = new CompressedDocument(currentDocument);
            case 3 -> {
                System.out.print("Введіть текст водяного знаку: ");
                String watermark = scanner.nextLine();
                currentDocument = new WatermarkedDocument(currentDocument, watermark);
            }
            case 4 -> decryptDocument();
            default -> System.out.println("Невірний вибір.");
        }

        if (choice != 4) {
            System.out.println("Декоратор застосовано.");
        }
    }

    private static void decryptDocument() {
        if (!(currentDocument instanceof EncryptedDocument)) {
            System.out.println("Поточний документ не є зашифрованим.");
            return;
        }

        try {
            String encryptedContent = currentDocument.getContent();
            String decryptedContent = EncryptedDocument.decrypt(encryptedContent);
            currentDocument = new TextDocument(decryptedContent);
            System.out.println("Документ успішно дешифровано.");
        } catch (Exception e) {
            System.out.println("Помилка дешифрування: " + e.getMessage());
        }
    }


    private static void manageLifecycle(Scanner scanner) {
        if (currentLifecycle == null) {
            System.out.println("Стан документа не ініціалізовано.");
            return;
        }

        while (true) {
            System.out.println("\nПоточний стан документа: " + currentLifecycle.getCurrentState());
            System.out.println("1. Перейти до наступного стану");
            System.out.println("2. Повернутися до меню операцій з файлом");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    try {
                        currentLifecycle.moveToNextState();
                        System.out.println("Стан змінено на: " + currentLifecycle.getCurrentState());
                    } catch (IllegalStateException e) {
                        System.out.println("Помилка: " + e.getMessage());
                    }
                }
                case 2 -> {
                    return;
                }
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

//    private static void saveLifecycleState() {
//        if (currentDocument instanceof FileDocument fileDocument) {
//            String newContent = currentDocument.getContent()
//                    + "\n[Стан документа: " + documentLifecycle.getCurrentState() + "]";
//            fileDocument.saveContent(newContent);
//            System.out.println("Стан документа збережено у файл.");
//        }
//    }

    private static void saveFileChanges() {
        if (currentFileDocument == null || currentDocument == null) {
            System.out.println("Файл або документ не завантажено.");
            return;
        }

        currentFileDocument.saveContent(currentDocument.getContent());
        System.out.println("Зміни збережено у файл: " + currentFileDocument.getFilePath());
    }

    private static void showFileInfo() {
        System.out.println("\n--- Інформація про файл ---");

        // Виводимо поточний стан документа
        if (currentLifecycle != null) {
            System.out.println("Поточний стан документа: " + currentLifecycle.getCurrentState());
        } else {
            System.out.println("Стан документа не ініціалізовано.");
        }

        // Виводимо застосовані декоратори
        System.out.println("Застосовані декоратори:");
        Document tempDocument = currentDocument;
        while (tempDocument instanceof DocumentDecorator decorator) {
            if (decorator instanceof EncryptedDocument) {
                System.out.println("- Зашифровано");
            } else if (decorator instanceof CompressedDocument) {
                System.out.println("- Стиснуто");
            } else if (decorator instanceof WatermarkedDocument watermarkedDocument) {
                System.out.println("- Водяний знак: " + watermarkedDocument.getWatermark());
            }
            tempDocument = decorator.document;
        }

        if (currentDocument instanceof FileDocument fileDocument) {
            System.out.println("Шлях до файлу: " + fileDocument.getFilePath());
        }
    }

}
