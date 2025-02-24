package de.ait;

import de.ait.core.FinanceManager;
import de.ait.utilities.RecordType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Slf4j
public class FinanceManagerApp {

    private FinanceManager financeManager;
    private Scanner sc;
    private DateTimeFormatter dateFormatter; // Форматтер для даты

    public FinanceManagerApp() {
        this.financeManager = new FinanceManager();
        this.sc = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"); // Формат даты: дд.ММ.гггг
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = sc.nextInt();
            sc.nextLine(); // Очистка буфера после nextInt()

            switch (choice) {
                case 1:
                    addRecord();
                    break;
                case 2:
                    displayAllRecords();
                    break;
                case 3:
                    calculateBalance();
                    break;
                case 4:
                    saveRecordsToFile();
                    break;
                case 5:
                    loadRecordsFromFile();
                    break;
                case 6:
                    running = false;
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
        sc.close();
    }

    private void printMenu() {
        System.out.println("1. Добавить запись");
        System.out.println("2. Показать все записи");
        System.out.println("3. Рассчитать баланс за период");
        System.out.println("4. Сохранить записи в файл");
        System.out.println("5. Загрузить записи из файла");
        System.out.println("6. Выйти");
        System.out.print("Выберите действие: ");
    }

    private void addRecord() {
        System.out.print("Введите тип записи (INCOME/EXPENSE): ");
        String typeStr = sc.nextLine().toUpperCase();
        RecordType type;
        try {
            type = RecordType.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный тип записи. Используйте INCOME или EXPENSE.");
            return;
        }

        System.out.print("Введите сумму: ");
        double amount;
        try {
            amount = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат суммы. Введите число.");
            return;
        }

        System.out.print("Введите описание: ");
        String description = sc.nextLine();

        System.out.print("Введите дату (дд.ММ.гггг): ");
        LocalDate date;
        try {
            String dateStr = sc.nextLine();
            date = LocalDate.parse(dateStr, dateFormatter); // Парсинг даты из строки
        } catch (DateTimeParseException e) {
            System.out.println("Неверный формат даты. Используйте формат дд.ММ.гггг");
            return;
        }

        financeManager.addRecord(type, amount, description, date);
    }

    private void displayAllRecords() {
        financeManager.getFinanceRecords().forEach(System.out::println);
    }

    private void calculateBalance() {
        System.out.print("Введите начальную дату (дд.ММ.гггг): ");
        LocalDate startDate;
        try {
            String startDateStr = sc.nextLine();
            startDate = LocalDate.parse(startDateStr, dateFormatter); // Парсинг начальной даты
        } catch (DateTimeParseException e) {
            System.out.println("Неверный формат даты. Используйте формат дд.ММ.гггг");
            return;
        }

        System.out.print("Введите конечную дату (дд.ММ.гггг): ");
        LocalDate endDate;
        try {
            String endDateStr = sc.nextLine();
            endDate = LocalDate.parse(endDateStr, dateFormatter); // Парсинг конечной даты
        } catch (DateTimeParseException e) {
            System.out.println("Неверный формат даты. Используйте формат дд.ММ.гггг");
            return;
        }

        double balance = financeManager.calculateBalance(startDate, endDate);
        System.out.println("Баланс за период с " + startDate + " по " + endDate + ": " + balance);
    }

    private void saveRecordsToFile() {
        System.out.print("Введите имя файла для сохранения: ");
        String fileName = sc.nextLine();
        try {
            financeManager.saveRecordsToFile(fileName);
            System.out.println("Записи успешно сохранены в файл: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении записей: " + e.getMessage());
        }
    }

    private void loadRecordsFromFile() {
        System.out.print("Введите имя файла для загрузки: ");
        String fileName = sc.nextLine();
        try {
            financeManager.loadRecordsFromFile(fileName);
            System.out.println("Записи успешно загружены из файла: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке записей: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        FinanceManagerApp app = new FinanceManagerApp();
        app.start();
    }
}