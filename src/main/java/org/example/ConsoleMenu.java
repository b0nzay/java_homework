package org.example;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final SingerRepository repository = new SingerRepository();
    private final SingerFileLoader loader = new SingerFileLoader();
    private final SingerService service = new SingerService();

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            if (!scanner.hasNextLine()) {
                break;
            }
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addSingerManually();
                case "2" -> loadFromFile();
                case "3" -> printAll();
                case "4" -> processWithStreams();
                case "5" -> showSingerByNumber();
                case "6" -> removeSingerByNumber();
                case "0" -> running = false;
                default -> System.out.println("Такого пункта нет, попробуйте еще раз");
            }
        }
        System.out.println("До свидания");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("=====Меню=====");
        System.out.println("1. Добавить певца вручную");
        System.out.println("2. Загрузить певцов из файла");
        System.out.println("3. Вывести всех певцов");
        System.out.println("4. Обработать данные (Stream API)");
        System.out.println("5. Показать певца по номеру");
        System.out.println("6. Удалить певца по номеру");
        System.out.println("0. Выход");
        System.out.print("Ваш выбор: ");
    }

    private void addSingerManually() {
        try {
            System.out.print("Имя: ");
            String name = scanner.nextLine();
            System.out.print("Жанр: ");
            String genre = scanner.nextLine();
            System.out.print("Год дебюта: ");
            int debutYear = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Кол-во альбомов: ");
            int albums = Integer.parseInt(scanner.nextLine().trim());

            repository.add(new Singer(name, genre, debutYear, albums));
            System.out.println("Певец добавлен");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: год и число альбомов должны быть целыми числами");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        System.out.print("Путь к файлу (Enter — singers.csv): ");
        String input = scanner.nextLine().trim();
        String fileName = input.isEmpty() ? "singers.csv" : input;

        try {
            MyArrayList<Singer> loaded = loader.load(Path.of(fileName));
            repository.addAll(loaded);
            System.out.println("Загружено певцов: " + loaded.size());
        } catch (NoSuchFileException e) {
            System.out.println("Файл не найден: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        } catch (InvalidPathException e) {
            System.out.println("Некорректный путь: " + fileName);
        }
    }

    private void printAll() {
        if (repository.isEmpty()) {
            System.out.println("Список пуст");
            return;
        }
        System.out.println("Всего певцов: " + repository.size());
        for (int i = 0; i < repository.size(); i++) {
            System.out.println((i + 1) + ". " + repository.get(i));
        }
    }

    private void processWithStreams() {
        if (repository.isEmpty()) {
            System.out.println("Список пуст, сначала добавьте певцов");
            return;
        }
        System.out.print("Введите жанр для фильтрации: ");
        String genre = scanner.nextLine().trim();

        List<String> result = service.findByGenreSortedByYear(repository.stream(), genre);

        if (result.isEmpty()) {
            System.out.println("Певцов жанра \"" + genre + "\" не найдено");
        } else {
            System.out.println("Певцы жанра \"" + genre + "\" по году дебюта:");
            result.forEach(System.out::println);
        }
    }

    private void showSingerByNumber() {
        Integer index = readIndex();
        if (index != null) {
            System.out.println(repository.get(index));
        }
    }

    private void removeSingerByNumber() {
        Integer index = readIndex();
        if (index != null) {
            Singer removed = repository.remove(index);
            System.out.println("Удалён: " + removed);
        }
    }

    private Integer readIndex() {
        if (repository.isEmpty()) {
            System.out.println("Список пуст");
            return null;
        }
        System.out.print("Номер певца (1–" + repository.size() + "): ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            repository.get(index);
            return index;
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: номер должен быть целым числом");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Ошибка: певца с таким номером нет");
        }
        return null;
    }
}
