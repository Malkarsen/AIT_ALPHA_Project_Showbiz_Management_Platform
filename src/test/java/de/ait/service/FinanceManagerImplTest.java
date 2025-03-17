package de.ait.service;

import de.ait.model.FinanceRecord;
import de.ait.utilities.CategoryType;
import de.ait.utilities.RecordType;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FinanceManagerImplTest {

    private FinanceManagerImpl financeManager;
    private static final String CSV_FILE = "src/main/java/de/ait/files/FinanceRecord.csv";
    private static final String SERIALIZED_FILE = "src/main/java/de/ait/files/FinanceRecord.cer";
    private static final String TEST_CSV_FILE = "test_finance_records.csv";
    private static final String TEST_SERIALIZED_FILE = "test_finance_records.cer";

    @BeforeEach
    void setUp() {
        new File (CSV_FILE).delete();
        new File(SERIALIZED_FILE).delete();
        new File(TEST_CSV_FILE).delete();
        new File(TEST_SERIALIZED_FILE).delete();
        financeManager = new FinanceManagerImpl();
    }

    @Test
    void testAddRecord_ValidData() {
        financeManager.addRecord(RecordType.INCOME, 1000, "Salary", LocalDate.now(), CategoryType.INCOME_SALARY);
        List<FinanceRecord> records = financeManager.getFinanceRecords();

        assertEquals(1, records.size());
        assertEquals(1000, records.get(0).getAmount());
        assertEquals(CategoryType.INCOME_SALARY, records.get(0).getCategory());
    }

    @Test
    void testAddRecord_InvalidAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                financeManager.addRecord(RecordType.EXPENSE, -50, "Food", LocalDate.now(), CategoryType.EXPENSE_FOOD));
        assertEquals("Amount must be greater than 0", exception.getMessage());
    }

    @Test
    void testAddRecord_FutureDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                financeManager.addRecord(RecordType.INCOME, 500, "Future Salary", LocalDate.now().plusDays(1), CategoryType.INCOME_OTHER));
        assertEquals("Date cannot be in the future", exception.getMessage());
    }

    @Test
    void testCalculateBalance() {
        financeManager.addRecord(RecordType.INCOME, 2000, "Freelance", LocalDate.now(), CategoryType.INCOME_BUSINESS);
        financeManager.addRecord(RecordType.EXPENSE, 500, "Rent", LocalDate.now(), CategoryType.EXPENSE_RENT);
        financeManager.addRecord(RecordType.EXPENSE, 300, "Groceries", LocalDate.now(), CategoryType.EXPENSE_FOOD);

        double balance = financeManager.calculateBalance(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        assertEquals(1200, balance);
    }

    @Test
    void testCalculateBalance_InvalidDateRange() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                financeManager.calculateBalance(LocalDate.now(), LocalDate.now().minusDays(1)));
        assertEquals("Start date cannot be after end date", exception.getMessage());
    }

    @Test
    void testSaveAndLoadRecords_CSV() throws IOException {
        financeManager.addRecord(RecordType.INCOME, 1500, "Consulting", LocalDate.now(), CategoryType.INCOME_BUSINESS);
        financeManager.saveRecordsToFile(TEST_CSV_FILE);

        FinanceManagerImpl newManager = new FinanceManagerImpl();
        newManager.loadRecordsFromFile(TEST_CSV_FILE);
        List<FinanceRecord> records = newManager.getFinanceRecords();

        assertEquals(1, records.size());
        assertEquals(1500, records.get(0).getAmount());
        assertEquals(CategoryType.INCOME_BUSINESS, records.get(0).getCategory());
    }

    @Test
    void testSaveAndLoadRecords_Serialized() {
        financeManager.addRecord(RecordType.EXPENSE, 200, "Gym", LocalDate.now(), CategoryType.EXPENSE_SPORT);
        financeManager.saveRecordsToFileSerialized();

        FinanceManagerImpl newManager = new FinanceManagerImpl();
        newManager.loadRecordsFromFileSerialized();
        List<FinanceRecord> records = newManager.getFinanceRecords();

        assertEquals(1, records.size());
        assertEquals(200, records.get(0).getAmount());
        assertEquals(CategoryType.EXPENSE_SPORT, records.get(0).getCategory());
    }

    @Test
    void testLoadRecords_FileNotFound() {
        FinanceManagerImpl newManager = new FinanceManagerImpl();
        Exception exception = assertThrows(IOException.class, () ->
                newManager.loadRecordsFromFile("non_existing_file.csv"));
        assertTrue(exception.getMessage().contains("File not found"));
    }
}