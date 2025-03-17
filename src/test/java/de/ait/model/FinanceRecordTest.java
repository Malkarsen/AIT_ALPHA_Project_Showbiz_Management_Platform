package de.ait.model;

import de.ait.utilities.CategoryType;
import de.ait.utilities.RecordType;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FinanceRecordTest {

    @Test
    void testCreateValidRecord() {
        FinanceRecord record = new FinanceRecord(RecordType.INCOME, 1000, "Salary", LocalDate.now(), CategoryType.INCOME_SALARY);

        assertNotNull(record.getId());
        assertEquals(RecordType.INCOME, record.getType());
        assertEquals(1000, record.getAmount());
        assertEquals("Salary", record.getDescription());
        assertEquals(LocalDate.now(), record.getDate());
        assertEquals(CategoryType.INCOME_SALARY, record.getCategory());
    }

    @Test
    void testInvalidAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new FinanceRecord(RecordType.EXPENSE, -500, "Rent", LocalDate.now(), CategoryType.EXPENSE_RENT));
        assertEquals("Amount must be greater than 0", exception.getMessage());
    }

    @Test
    void testInvalidDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new FinanceRecord(RecordType.INCOME, 500, "", LocalDate.now(), CategoryType.INCOME_OTHER));
        assertEquals("Description cannot be empty", exception.getMessage());
    }

    @Test
    void testFutureDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new FinanceRecord(RecordType.INCOME, 500, "Future Salary", LocalDate.now().plusDays(1), CategoryType.INCOME_OTHER));
        assertEquals("Date cannot be in the future", exception.getMessage());
    }

    @Test
    void testNullCategory() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new FinanceRecord(RecordType.INCOME, 100, "Gift", LocalDate.now(), null));
        assertEquals("CategoryType cannot be null", exception.getMessage());
    }

    @Test
    void testUniqueIDGeneration() {
        FinanceRecord record1 = new FinanceRecord(RecordType.EXPENSE, 50, "Lunch", LocalDate.now(), CategoryType.EXPENSE_FOOD);
        FinanceRecord record2 = new FinanceRecord(RecordType.EXPENSE, 75, "Dinner", LocalDate.now(), CategoryType.EXPENSE_FOOD);

        assertNotNull(record1.getId());
        assertNotNull(record2.getId());
        assertNotEquals(record1.getId(), record2.getId());
    }

    @Test
    void testToStringFormat() {
        FinanceRecord record = new FinanceRecord(RecordType.INCOME, 1200, "Freelance", LocalDate.of(2024, 3, 15), CategoryType.INCOME_BUSINESS);
        String expected = "FinanceRecord{id='" + record.getId() + "', type=INCOME, amount=1200.0, description='Freelance', date=15.03.2024, category=INCOME_BUSINESS}";
        assertEquals(expected, record.toString());
    }
}