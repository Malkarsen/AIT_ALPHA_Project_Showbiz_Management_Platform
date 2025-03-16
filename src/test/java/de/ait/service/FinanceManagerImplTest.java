package de.ait.service;

import de.ait.model.FinanceRecord;
import de.ait.utilities.RecordType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FinanceManagerImplTest {

    private FinanceManagerImpl financeManagerImpl;

    @BeforeEach
    void setUp() {
        financeManagerImpl = new FinanceManagerImpl();
    }

    @Test
    void testAddRecord() {
        FinanceRecord record = new FinanceRecord(RecordType.INCOME, 100.0, "Salary", LocalDate.of(2025, 2, 26));
        financeManagerImpl.addRecord(record.getType(), record.getAmount(), record.getDescription(), record.getDate());

        List<FinanceRecord> records = financeManagerImpl.getFinanceRecords();
        assertEquals(1, records.size());

        FinanceRecord addedRecord = records.get(0);
        assertEquals(record.getType(), addedRecord.getType());
        assertEquals(record.getAmount(), addedRecord.getAmount());
        assertEquals(record.getDescription(), addedRecord.getDescription());
        assertEquals(record.getDate(), addedRecord.getDate());
    }

    @Test
    void testCalculateBalance() {
        financeManagerImpl.addRecord(RecordType.INCOME, 100.0, "Salary", LocalDate.now());
        financeManagerImpl.addRecord(RecordType.EXPENSE, 50.0, "Groceries", LocalDate.now());

        double balance = financeManagerImpl.calculateBalance(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        assertEquals(50.0, balance);
    }

    @Test
    void testCalculateBalance_NoRecords() {
        double balance = financeManagerImpl.calculateBalance(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        assertEquals(0.0, balance);
    }

    @Test
    void testCalculateBalance_InvalidDateRange() {
        financeManagerImpl.addRecord(RecordType.INCOME, 100.0, "Salary", LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> {
            financeManagerImpl.calculateBalance(LocalDate.now().plusDays(1), LocalDate.now());
        });
    }

    @Test
    void testSaveAndLoadRecords() throws IOException {
        financeManagerImpl.addRecord(RecordType.INCOME, 100.0, "Salary", LocalDate.of(2025, 2, 26));
        financeManagerImpl.addRecord(RecordType.EXPENSE, 50.0, "Groceries", LocalDate.of(2025, 2, 26));

        String fileName = "src/test/resources/testRecords.ser";
        financeManagerImpl.saveRecordsToFile(fileName);

        FinanceManagerImpl newFinanceManagerImpl = new FinanceManagerImpl();
        newFinanceManagerImpl.loadRecordsFromFile(fileName);

        List<FinanceRecord> originalRecords = financeManagerImpl.getFinanceRecords();
        List<FinanceRecord> loadedRecords = newFinanceManagerImpl.getFinanceRecords();

        assertEquals(originalRecords.size(), loadedRecords.size());
        for (int i = 0; i < originalRecords.size(); i++) {
            assertEquals(originalRecords.get(i).getType(), loadedRecords.get(i).getType());
            assertEquals(originalRecords.get(i).getAmount(), loadedRecords.get(i).getAmount());
            assertEquals(originalRecords.get(i).getDescription(), loadedRecords.get(i).getDescription());
            assertEquals(originalRecords.get(i).getDate(), loadedRecords.get(i).getDate());
        }
    }
}