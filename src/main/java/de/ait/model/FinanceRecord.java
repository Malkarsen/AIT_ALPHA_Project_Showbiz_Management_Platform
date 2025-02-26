package de.ait.model;

import de.ait.utilities.RecordType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
public class FinanceRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final RecordType type;
    private double amount;
    private final String description;
    private final LocalDate date;
    private transient DateTimeFormatter dateFormatter;

    public FinanceRecord(RecordType type, double amount, String description, LocalDate date) {
        if (type == null) {
            log.error("Error: RecordType provided as null");
            throw new IllegalArgumentException("RecordType cannot be null");
        }
        if (amount <= 0) {
            log.error("Error: Invalid amount (amount={}): amount must be greater than 0", amount);
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (description == null || description.trim().isEmpty()) {
            log.error("Error: Empty or null description");
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (date == null || date.isAfter(LocalDate.now())) {
            log.error("Error: Invalid date (date={}): date cannot be in the future", date);
            throw new IllegalArgumentException("Date cannot be in the future");
        }

        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); //
        this.dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    // Геттеры и другие методы
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