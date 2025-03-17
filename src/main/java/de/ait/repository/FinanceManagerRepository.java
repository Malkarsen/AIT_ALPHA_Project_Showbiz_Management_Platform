package de.ait.repository;

import de.ait.model.FinanceRecord;
import de.ait.utilities.CategoryType;
import de.ait.utilities.RecordType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface FinanceManagerRepository {
    void addRecord(RecordType type, double amount, String description, LocalDate date, CategoryType category);

    double calculateBalance(LocalDate startDate, LocalDate endDate);

    void saveRecordsToFile(String fileName) throws IOException;

    void loadRecordsFromFile(String fileName) throws IOException;

    List<FinanceRecord> getFinanceRecords();

    void saveRecordsToFileSerialized();
    void loadRecordsFromFileSerialized();
    void clearRecordsOnExit();
}
