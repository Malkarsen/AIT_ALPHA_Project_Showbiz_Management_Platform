package de.ait.model;

import de.ait.utilities.RecordType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Getter
@Setter
public class FinanceRecord {


    private final String id;
    private final RecordType type;
    private double amount;
    private final String description;
    private final LocalDate date;
    private static DateTimeFormatter dateFormatter;

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

            log.error("Error: Invalid date (date={}): date cannot be in the future", date.format(dateFormatter));
            throw new IllegalArgumentException("Date cannot be in the future");
        }

        this.id = UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 16); // Generate a unique identifier;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FinanceRecord{");
        sb.append("id='").append(id).append('\'');
        sb.append(", type=").append(type);
        sb.append(", amount=").append(amount);
        sb.append(", description='").append(description).append('\'');
        sb.append(", date=").append(date.format(dateFormatter));
        sb.append('}');
        return sb.toString();
    }
}