package de.ait.core;

import de.ait.model.FinanceRecord;
import de.ait.utilities.RecordType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FinanceManagerTest {

    private FinanceManager financeManager;

    @BeforeEach
    void setUp() {
        financeManager = new FinanceManager();
    }

    @Test
    void testAddRecord() {
        FinanceRecord record = new FinanceRecord(RecordType.INCOME, 100.0, "Salary", LocalDate.of(2025, 2, 26));
        financeManager.addRecord(record.getType(), record.getAmount(), record.getDescription(), record.getDate());

        List<FinanceRecord> records = financeManager.getFinanceRecords();
        assertEquals(1, records.size());

        FinanceRecord addedRecord = records.get(0);
        assertEquals(record.getType(), addedRecord.getType());
        assertEquals(record.getAmount(), addedRecord.getAmount());
        assertEquals(record.getDescription(), addedRecord.getDescription());
        assertEquals(record.getDate(), addedRecord.getDate());
    }

    @Test
    void testCalculateBalance() {
        financeManager.addRecord(RecordType.INCOME, 100.0, "Salary", LocalDate.now());
        financeManager.addRecord(RecordType.EXPENSE, 50.0, "Groceries", LocalDate.now());

        double balance = financeManager.calculateBalance(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        assertEquals(50.0, balance);
    }

    @Test
    void testCalculateBalance_NoRecords() {
        double balance = financeManager.calculateBalance(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        assertEquals(0.0, balance);
    }

    @Test
    void testCalculateBalance_InvalidDateRange() {
        financeManager.addRecord(RecordType.INCOME, 100.0, "Salary", LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> {
            financeManager.calculateBalance(LocalDate.now().plusDays(1), LocalDate.now());
        });
    }

    @Test
    void testSaveAndLoadRecords() throws IOException {
        financeManager.addRecord(RecordType.INCOME, 100.0, "Salary", LocalDate.of(2025, 2, 26));
        financeManager.addRecord(RecordType.EXPENSE, 50.0, "Groceries", LocalDate.of(2025, 2, 26));

        String fileName = "src/test/resources/testRecords.ser";
        financeManager.saveRecordsToFile(fileName);

        FinanceManager newFinanceManager = new FinanceManager();
        newFinanceManager.loadRecordsFromFile(fileName);

        List<FinanceRecord> originalRecords = financeManager.getFinanceRecords();
        List<FinanceRecord> loadedRecords = newFinanceManager.getFinanceRecords();

        assertEquals(originalRecords.size(), loadedRecords.size());
        for (int i = 0; i < originalRecords.size(); i++) {
            assertEquals(originalRecords.get(i).getType(), loadedRecords.get(i).getType());
            assertEquals(originalRecords.get(i).getAmount(), loadedRecords.get(i).getAmount());
            assertEquals(originalRecords.get(i).getDescription(), loadedRecords.get(i).getDescription());
            assertEquals(originalRecords.get(i).getDate(), loadedRecords.get(i).getDate());
        }
    }
}