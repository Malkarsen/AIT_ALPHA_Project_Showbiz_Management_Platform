package de.ait.core;

import de.ait.model.FinanceRecord;
import de.ait.utilities.RecordType;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The FinanceManager class manages financial records.
 * It allows adding, saving, loading, and calculating financial balances.
 */
@Slf4j
public class FinanceManager {
    private List<FinanceRecord> financeRecords;

    /**
     * Initializes FinanceManager with an empty list of financial records.
     */
    public FinanceManager() {
        this.financeRecords = new ArrayList<>();
        log.info("FinanceManager initialized. An empty list of financial records created.");
    }

    /**
     * Adds a new financial record.
     *
     * @param type        The type of record (INCOME or EXPENSE).
     * @param amount      The monetary amount of the record.
     * @param description A brief description of the record.
     * @param date        The date of the record.
     */
    public void addRecord(RecordType type, double amount, String description, LocalDate date) {
        FinanceRecord record = new FinanceRecord(type, amount, description, date);
        financeRecords.add(record);
        log.info("New record added: {}", record);
    }

    /**
     * Calculates the financial balance within a given period.
     *
     * @param startDate The start date of the period.
     * @param endDate   The end date of the period.
     * @return The balance calculated as income minus expenses.
     * @throws IllegalArgumentException If the start date is after the end date.
     */
    public double calculateBalance(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            log.error("Invalid date range: startDate {} is after endDate {}", startDate, endDate);
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        double totalIncome = financeRecords.stream()
                .filter(record -> record.getType() == RecordType.INCOME)
                .filter(record -> !record.getDate().isBefore(startDate) && !record.getDate().isAfter(endDate))
                .mapToDouble(FinanceRecord::getAmount)
                .sum();

        double totalExpenses = financeRecords.stream()
                .filter(record -> record.getType() == RecordType.EXPENSE)
                .filter(record -> !record.getDate().isBefore(startDate) && !record.getDate().isAfter(endDate))
                .mapToDouble(FinanceRecord::getAmount)
                .sum();

        double balance = totalIncome - totalExpenses;
        log.info("Balance calculated for the period from {} to {}: {}", startDate, endDate, balance);
        return balance;
    }

    /**
     * Saves financial records to a CSV file.
     *
     * @param fileName The path of the file to save the records.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void saveRecordsToFile(String fileName) throws IOException {
        if (financeRecords.isEmpty()) {
            log.warn("The financial records list is empty. Nothing will be saved.");
            return;
        }

        Path filePath = Paths.get(fileName);
        Files.createDirectories(filePath.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write("Type,Amount,Description,Date");
            writer.newLine();

            for (FinanceRecord record : financeRecords) {
                writer.write(record.getType() + "," + record.getAmount() + "," +
                        record.getDescription() + "," + record.getDate());
                writer.newLine();
            }

            log.info("Financial records saved to CSV file: {}", fileName);
        } catch (IOException e) {
            log.error("Error saving records to CSV file: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Loads financial records from a CSV file.
     *
     * @param fileName The path of the file to load the records from.
     * @throws IOException If an error occurs while reading the file.
     */
    public void loadRecordsFromFile(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        if (!Files.exists(filePath)) {
            log.error("CSV file not found: {}", fileName);
            throw new FileNotFoundException("File not found: " + fileName);
        }

        List<FinanceRecord> records = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length == 4) {
                    RecordType type = RecordType.valueOf(fields[0]);
                    double amount = Double.parseDouble(fields[1]);
                    String description = fields[2];
                    LocalDate date = LocalDate.parse(fields[3]);

                    records.add(new FinanceRecord(type, amount, description, date));
                }
            }

            this.financeRecords = records;
            log.info("Financial records loaded from CSV file: {}", fileName);
        } catch (IOException e) {
            log.error("Error loading records from CSV file: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Returns a copy of the financial records list.
     *
     * @return A list of financial records.
     */
    public List<FinanceRecord> getFinanceRecords() {
        log.debug("Returning a copy of the financial records list.");
        return new ArrayList<>(financeRecords);
    }
}
