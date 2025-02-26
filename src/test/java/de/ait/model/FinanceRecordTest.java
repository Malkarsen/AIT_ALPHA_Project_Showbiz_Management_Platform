package de.ait.model;

import de.ait.utilities.RecordType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FinanceRecordTest {

    @Test
    void testFinanceRecordCreation() {
        FinanceRecord record = new FinanceRecord(RecordType.INCOME, 100.0, "Salary", LocalDate.now());

        assertNotNull(record.getId());
        assertEquals(RecordType.INCOME, record.getType());
        assertEquals(100.0, record.getAmount());
        assertEquals("Salary", record.getDescription());
        assertEquals(LocalDate.now(), record.getDate());
    }

    @Test
    void testFinanceRecordInvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FinanceRecord(RecordType.INCOME, -100.0, "Salary", LocalDate.now());
        });
    }

    @Test
    void testFinanceRecordInvalidDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FinanceRecord(RecordType.INCOME, 100.0, "", LocalDate.now());
        });
    }

    @Test
    void testFinanceRecordInvalidDate() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FinanceRecord(RecordType.INCOME, 100.0, "Salary", LocalDate.now().plusDays(1));
        });
    }
}