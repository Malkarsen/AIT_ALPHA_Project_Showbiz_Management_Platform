package de.ait.model;

import de.ait.utilities.CategoryType;
import de.ait.utilities.RecordType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Getter
@Setter
public class FinanceRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;
    private final RecordType type;
    private double amount;
    private final String description;
    private final LocalDate date;

    private CategoryType category; // added category

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    public FinanceRecord(RecordType type, double amount, String description, LocalDate date, CategoryType category) {
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
        if (category == null) {
            log.error("Error: CategoryType provided as null");
            throw new IllegalArgumentException("CategoryType cannot be null");
        }

        this.id = generateNumericUUID(); // Generation of unique identifier
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.category = category;
    }

    /**
     * Generates a unique numeric 16-character ID from UUID.
     */
    private static String generateNumericUUID() {
        String numericUUID = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        return String.format("%-16s", numericUUID).replace(' ', '0').substring(0, 16);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FinanceRecord{");
        sb.append("id='").append(id).append('\'');
        sb.append(", type=").append(type);
        sb.append(", amount=").append(amount);
        sb.append(", description='").append(description).append('\'');
        sb.append(", date=").append(date.format(dateFormatter));
        sb.append(", category=").append(category);
        sb.append('}');
        return sb.toString();
    }
}