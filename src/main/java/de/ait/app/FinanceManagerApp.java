package de.ait.app;

import de.ait.service.FinanceManagerImpl;
import de.ait.utilities.CategoryType;
import de.ait.utilities.RecordType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Slf4j
public class FinanceManagerApp {

    private static final FinanceManagerImpl financeManager = new FinanceManagerImpl();
    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    public boolean start() {
        byte choice;
        boolean run = true;
        while (run) {

            showMenu();
            choice = inputChoice();

            switch (choice) {
                case 1 -> addRecord();
                case 2 -> displayAllRecords();
                case 3 -> calculateBalance();
                case 4 -> saveRecordsToFile();
                case 5 -> loadRecordsFromFile();
                case 6 -> {

                    log.warn("Quit the program FinanceManagerApp");
                    System.out.println("Exiting the program.");
                    run = false;
                    return false; // return signal for general menu APP
                }
                default -> {
                    log.warn("Invalid choice. Please try again.");
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        }
        return true;
    }

    private static void showMenu() {
        System.out.println("1. Add record");
        System.out.println("2. Show all records");
        System.out.println("3. Calculate balance for a period");
        System.out.println("4. Save records to file");
        System.out.println("5. Load records from file");
        System.out.println("6. Exit");
        System.out.print("Choose an action: ");
    }

    private static void addRecord() {
        System.out.print("Enter record type (INCOME/EXPENSE): ");
        String typeStr = sc.nextLine().toUpperCase();
        RecordType type;
        try {
            type = RecordType.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid record type. Use INCOME or EXPENSE.");
            System.err.println("Invalid record type. Use INCOME or EXPENSE.");
            return;
        }

        System.out.print("Enter amount: ");
        double amount;
        try {
            amount = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            log.warn("Invalid amount format. Enter a number.");
            System.err.println("Invalid amount format. Enter a number.");
            return;
        }
        // checking for positive number
        if (amount <= 0) {
            log.warn("Invalid amount entered: {}. Amount must be greater than 0.", amount);
            System.err.println("Error: Amount must be greater than 0. Please enter a valid amount.");
            return;
        }

        System.out.print("Enter description: ");
        String description = sc.nextLine();
        if (description.trim().isEmpty()) {
            log.warn("The field cannot be empty. Enter a description of the operation");
            System.err.println("The field cannot be empty. Enter a description of the operation");
            return;
        }

        System.out.print("Enter date (dd.MM.yyyy): ");
        LocalDate date;
        try {
            String dateStr = sc.nextLine();
            date = LocalDate.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            log.warn("Invalid date format. Use dd.MM.yyyy");
            System.err.println("Invalid date format. Use dd.MM.yyyy");
            return;
        }
        log.debug("Attempting to add record - Type: {}, Amount: {}, Description: {}, Date: {}", type, amount, description, date.format(dateFormatter));
        if (date.isAfter(LocalDate.now())) {
            log.warn("User attempted to add a record with a future date: {}", date.format(dateFormatter));
            System.err.println("Error: The date cannot be in the future. Please enter a valid date.");
            return; // do not add record and dont call mistake
        }
        // Filter categories by type (income/expense)
        System.out.println("Select a category:");
        for (CategoryType cat : CategoryType.values()) {
            if ((type == RecordType.INCOME && cat.name().startsWith("INCOME_")) ||
                    (type == RecordType.EXPENSE && cat.name().startsWith("EXPENSE_"))) {
                System.out.println("- " + cat);
            }
        }
        System.out.print("Enter category: ");
        String categoryStr = sc.nextLine().toUpperCase();
        CategoryType category;
        try {
            category = CategoryType.valueOf(categoryStr);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid category. Using default category EXPENSE_OTHER.");
            category = (type == RecordType.INCOME) ? CategoryType.INCOME_OTHER : CategoryType.EXPENSE_OTHER;
        }

        financeManager.addRecord(type, amount, description, date, category);
    }

    private static void displayAllRecords() {
        financeManager.getFinanceRecords().forEach(System.out::println);
    }

    private static void calculateBalance() {
        System.out.print("Enter start date (dd.MM.yyyy): ");
        LocalDate startDate;
        try {
            String startDateStr = sc.nextLine();
            startDate = LocalDate.parse(startDateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            log.warn("Invalid date format. Use dd.MM.yyyy");
            System.err.println("Invalid date format. Use dd.MM.yyyy");
            return;
        }

        System.out.print("Enter end date (dd.MM.yyyy): ");
        LocalDate endDate;
        try {
            String endDateStr = sc.nextLine();
            endDate = LocalDate.parse(endDateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            log.warn("Invalid date format. Use dd.MM.yyyy");
            System.err.println("Invalid date format. Use dd.MM.yyyy");
            return;
        }

        double balance = financeManager.calculateBalance(startDate, endDate);
        System.out.println("Balance for the period from " + startDate + " to " + endDate + ": " + balance);
    }

    private static void saveRecordsToFile() {
        String fileName = "src/main/java/de/ait/files/FinanceRecord.csv";
        System.out.print("Save records to file: " + fileName);
        try {
            financeManager.saveRecordsToFile(fileName);
            System.out.println("Records successfully saved to file: " + fileName);
        } catch (IOException e) {
            log.error("Error saving records: " + e.getMessage());
            System.err.println("Error saving records: " + e.getMessage());
        }
    }

    private static void loadRecordsFromFile() {
        String fileName = "src/main/java/de/ait/files/FinanceRecord.csv";
        System.out.print("Load records from file: " + fileName);
        try {
            financeManager.loadRecordsFromFile(fileName);
            System.out.println("Records successfully loaded from file: " + fileName);
        } catch (IOException e) {
            log.error("Error loading records: " + e.getMessage());
            System.out.println("Error loading records: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        FinanceManagerApp app = new FinanceManagerApp();
        app.start();
    }

    private static byte inputChoice() {
        try {
            byte choice = sc.nextByte();
            sc.nextLine();
            return choice;
        } catch (NoSuchElementException e) {
            log.warn("Invalid input");
            System.out.println("Invalid input");
            return 0;
        }
    }
}