package de.ait.core;

import de.ait.model.FinanceRecord;
import de.ait.utilities.RecordType;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FinanceManager {
    private List<FinanceRecord> financeRecords; // List of financial records
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"); // Date formatter

    public FinanceManager() {
        this.financeRecords = new ArrayList<>();
        log.info("FinanceManager initialized. Record list created.");
    }

    /**
     * Adds a new financial record (income or expense).
     *
     * @param type        Record type (INCOME or EXPENSE).
     * @param amount      Amount.
     * @param description Description.
     * @param date        Date.
     */
    public void addRecord(RecordType type, double amount, String description, LocalDate date) {
        FinanceRecord record = new FinanceRecord(type, amount, description, date);
        financeRecords.add(record);
        log.info("New record added: {}", record);
    }

    /**
     * Calculates the balance (difference between income and expenses) for the specified period.
     *
     * @param startDate Start date of the period
     * @param endDate   End date of the period
     * @return Final balance for the period (income minus expenses)
     * @throws IllegalArgumentException if the start date is later than the end date
     */
    public double calculateBalance(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            log.error("Invalid date range: startDate {} is after endDate {}", startDate.format(dateFormatter), endDate.format(dateFormatter));
            throw new IllegalArgumentException("Start date cannot be later than end date");
        }

        double totalIncome = financeRecords.stream()
                .filter(record -> record.getType() == RecordType.INCOME)
                .filter(record -> !record.getDate().isBefore(startDate) && !record.getDate().isAfter(endDate))
                .mapToDouble(FinanceRecord::getAmount)
                .sum();

        double totalExpense = financeRecords.stream()
                .filter(record -> record.getType() == RecordType.EXPENSE)
                .filter(record -> !record.getDate().isBefore(startDate) && !record.getDate().isAfter(endDate))
                .mapToDouble(FinanceRecord::getAmount)
                .sum();

        double balance = totalIncome - totalExpense;
        log.info("Balance calculated for the period from {} to {}: {}", startDate.format(dateFormatter), endDate.format(dateFormatter), balance);
        return balance;
    }

    /**
     * Saves all financial records to a file.
     *
     * @param fileName File name for saving.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void saveRecordsToFile(String fileName) throws IOException {
        if (financeRecords.isEmpty()) {
            log.warn("The financial records list is empty. Nothing will be saved.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (FinanceRecord record : financeRecords) {
                writer.write(record.toString());
                writer.newLine();
            }
            log.info("Financial records saved to file: {}", fileName);
        } catch (IOException e) {
            log.error("Error saving records to file: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Loads financial records from a file.
     *
     * @param fileName File name for loading.
     * @throws IOException If an error occurs while reading the file.
     */
    public void loadRecordsFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            log.error("File not found: {}", fileName);
            throw new FileNotFoundException("File not found: " + fileName);
        }
        financeRecords.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                FinanceRecord record = parseRecordFromString(line);
                financeRecords.add(record);
            }
            log.info("Financial records loaded from file: {}", fileName);
        } catch (IOException e) {
            log.error("Error loading records from file: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Parses a string and creates a FinanceRecord object.
     *
     * @param line A line from the file.
     * @return FinanceRecord object.
     */
    private FinanceRecord parseRecordFromString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) {
            log.error("Invalid string format: {}", line);
            throw new IllegalArgumentException("Invalid string format: " + line);
        }

        RecordType type;
        try {
            type = RecordType.valueOf(parts[0]);
        } catch (IllegalArgumentException e) {
            log.error("Invalid record type: {}", parts[0]);
            throw new IllegalArgumentException("Invalid record type: " + parts[0]);
        }

        double amount;
        try {
            amount = Double.parseDouble(parts[1]);
        } catch (NumberFormatException e) {
            log.error("Invalid amount: {}", parts[1]);
            throw new IllegalArgumentException("Invalid amount: " + parts[1]);
        }

        String description = parts[2];
        if (description.trim().isEmpty()) {
            log.error("Description cannot be empty");
            throw new IllegalArgumentException("Description cannot be empty");
        }

        LocalDate date;
        try {
            date = LocalDate.parse(parts[3], dateFormatter); // Using formatter for correct date format
        } catch (Exception e) {
            log.error("Invalid date: {}", parts[3]);
            throw new IllegalArgumentException("Invalid date: " + parts[3]);
        }

        return new FinanceRecord(type, amount, description, date);
    }

    /**
     * Returns the list of all financial records.
     *
     * @return List of records.
     */
    public List<FinanceRecord> getFinanceRecords() {
        return new ArrayList<>(financeRecords);
    }
}