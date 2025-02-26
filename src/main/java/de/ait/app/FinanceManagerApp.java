package de.ait;

import de.ait.core.FinanceManager;
import de.ait.utilities.RecordType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Slf4j
public class FinanceManagerApp {

    private FinanceManager financeManager;
    private Scanner sc;
    private DateTimeFormatter dateFormatter;

    public FinanceManagerApp() {
        this.financeManager = new FinanceManager();
        this.sc = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = sc.nextInt();
            sc.nextLine(); // Clear buffer after nextInt()

            switch (choice) {
                case 1 -> addRecord();
                case 2 -> displayAllRecords();
                case 3 -> calculateBalance();
                case 4 -> saveRecordsToFile();
                case 5 -> loadRecordsFromFile();
                case 6 -> {
                    running = false;
                    log.info("Program terminated by user.");
                    System.out.println("Exiting the program.");
                }
                default -> {
                    log.warn("Invalid choice. Please try again.");
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        }
        sc.close();
    }

    private void printMenu() {
        System.out.println("1. Add record");
        System.out.println("2. Show all records");
        System.out.println("3. Calculate balance for a period");
        System.out.println("4. Save records to file");
        System.out.println("5. Load records from file");
        System.out.println("6. Exit");
        System.out.print("Choose an action: ");
    }

    private void addRecord() {
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

        financeManager.addRecord(type, amount, description, date);
    }

    private void displayAllRecords() {
        financeManager.getFinanceRecords().forEach(System.out::println);
    }

    private void calculateBalance() {
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

    private void saveRecordsToFile() {
        String fileName = "src/main/java/de/ait/files/FinanceRecord.ser"; // .ser extension for serialized files
        System.out.print("Save records to file: " + fileName);
        try {
            financeManager.saveRecordsToFile(fileName);
            System.out.println("Records successfully saved to file: " + fileName);
        } catch (IOException e) {
            log.error("Error saving records: " + e.getMessage());
            System.err.println("Error saving records: " + e.getMessage());
        }
    }

    private void loadRecordsFromFile() {
        String fileName = "src/main/java/de/ait/files/FinanceRecord.ser";
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
}