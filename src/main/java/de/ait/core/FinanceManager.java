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

@Slf4j // Lombok annotation to automatically generate a logger
public class FinanceManager {
    private List<FinanceRecord> financeRecords;

    public FinanceManager() {
        this.financeRecords = new ArrayList<>();
        log.info("FinanceManager initialized. An empty list of financial records created.");
    }

    public void addRecord(RecordType type, double amount, String description, LocalDate date) {
        FinanceRecord record = new FinanceRecord(type, amount, description, date);
        financeRecords.add(record);
        log.info("New record added: {}", record);
    }

    /**
     * Calculates the balance (difference between income and expenses) for a given period.
     *
     * @param startDate The start date of the period.
     * @param endDate   The end date of the period.
     * @return The balance for the period (income - expenses).
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
     * Saves the list of financial records to a file using serialization.
     *
     * @param fileName The name of the file to save to.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void saveRecordsToFile(String fileName) throws IOException {
        if (financeRecords.isEmpty()) {
            log.warn("The financial records list is empty. Nothing will be saved.");
            return;
        }

        Path filePath = Paths.get(fileName);
        Files.createDirectories(filePath.getParent()); // Create the directory if it doesn't exist

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            oos.writeObject(financeRecords); // Serialize the entire list
            log.info("Financial records saved to file: {}", fileName);
        } catch (IOException e) {
            log.error("Error saving records to file: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Loads the list of financial records from a file using deserialization.
     *
     * @param fileName The name of the file to load from.
     * @throws IOException If an error occurs while reading the file.
     */
    public void loadRecordsFromFile(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        if (!Files.exists(filePath)) {
            log.error("File not found: {}", fileName);
            throw new FileNotFoundException("File not found: " + fileName);
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(filePath))) {
            financeRecords = (List<FinanceRecord>) ois.readObject(); // Deserialize the list
            log.info("Financial records loaded from file: {}", fileName);
        } catch (ClassNotFoundException e) {
            log.error("Error loading records from file: {}", e.getMessage());
            throw new IOException("Class not found during deserialization", e);
        }
    }

    public List<FinanceRecord> getFinanceRecords() {
        log.debug("Returning a copy of the financial records list.");
        return new ArrayList<>(financeRecords);
    }
}