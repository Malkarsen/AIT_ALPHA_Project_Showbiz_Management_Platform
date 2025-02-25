package de.ait.model;

import de.ait.utilities.RecordType;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * This class stores information about financial records (income/expenses).
 */
@Slf4j
public class FinanceRecord {

    private final String id; // Unique identifier. Generated automatically.
    private final RecordType type; // Record type: income/expense
    private double amount; // Amount. Must be positive.
    private final String description; // Description. Cannot be empty.
    private final LocalDate date; // Date. Cannot be in the future.
    private DateTimeFormatter dateFormatter; // Date formatter

    /**
     * Constructor for creating a financial record.
     *
     * @param type        Record type (INCOME or EXPENSE). Cannot be null.
     * @param amount      Amount. Must be greater than 0.
     * @param description Description. Cannot be null or empty.
     * @param date        Date. Cannot be null or in the future.
     * @throws IllegalArgumentException If the data is incorrect.
     * @throws NullPointerException     If null values are provided.
     */
    public FinanceRecord(RecordType type, double amount, String description, LocalDate date) {

        // Check record type (enum)
        if (type == null) {
            log.error("Error: RecordType provided as null");
            throw new IllegalArgumentException("RecordType cannot be null");
        }

        // Check amount (must be > 0)
        if (amount <= 0) {
            log.error("Error: Invalid amount (amount={}): amount must be greater than 0", amount);
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        // Check description (must not be empty or contain only spaces)
        if (description == null || description.trim().isEmpty()) {
            log.error("Error: Empty or null description");
            throw new IllegalArgumentException("Description cannot be empty");
        }

        // Check date (must not be in the future)
        if (date == null || date.isAfter(LocalDate.now())) {
            log.error("Error: Invalid date (date={}): date cannot be in the future", date);
            throw new IllegalArgumentException("Date cannot be in the future");
        }

        this.id = UUID.randomUUID().toString(); // Generate a unique ID
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"); // Date format: dd.mm.yyyy

        log.debug("New financial record created: {}", this);
    }

    public String getId() {
        return id;
    }

    public RecordType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets a new amount for the record.
     *
     * @param amount New amount. Must be greater than 0.
     * @throws IllegalArgumentException If the amount is invalid.
     */
    public void setAmount(double amount) {
        if (amount <= 0) { // Check for positive value
            log.error("Attempt to set an invalid amount: {}", amount);
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        this.amount = amount;
        log.debug("Amount set to: {}", amount);
    }
    @Override
    public String toString() {
        return "FinanceRecord{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date.format(dateFormatter) +
                '}';
    }
}