package de.ait.model;

import de.ait.utilities.ContractTerms;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {

    private Contract contract;

    @BeforeEach
    void setUp() {
        contract = new Contract("Artist A", LocalDate.now(), LocalDate.now().plusDays(30), ContractTerms.STANDARD);
    }

    @Test
    void testContractCreation() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(30);

        assertNotNull(contract.getId());
        assertTrue(contract.getId().matches("\\d{16}"));

        assertEquals("Artist A", contract.getArtistName());
        assertEquals(startDate, contract.getStartDate());
        assertEquals(endDate, contract.getEndDate());
        assertEquals(ContractTerms.STANDARD, contract.getTerms());
    }

    @Test
    void testSetValidArtistName() {
        String originalId = contract.getId();
        ContractTerms originalTerms = contract.getTerms();
        contract.setArtistName("Artist B");
        assertEquals("Artist B", contract.getArtistName());
        assertEquals(originalId, contract.getId());
        assertEquals(originalTerms, contract.getTerms());
    }

    @Test
    void testSetInvalidArtistNameThrowsException() {
        Exception exceptionEmpty = assertThrows(IllegalArgumentException.class, () -> contract.setArtistName(""));
        assertEquals("Error: Artist name cannot be empty.", exceptionEmpty.getMessage());

        Exception exceptionNull = assertThrows(IllegalArgumentException.class, () -> contract.setArtistName(null));
        assertEquals("Error: Artist name cannot be empty.", exceptionNull.getMessage());
    }

    @Test
    void testSetValidDates() {
        String originalId = contract.getId();
        LocalDate newStartDate = LocalDate.now().plusDays(5);
        LocalDate newEndDate = LocalDate.now().plusDays(60);

        contract.setStartDate(newStartDate);
        assertEquals(newStartDate, contract.getStartDate());

        contract.setEndDate(newEndDate);
        assertEquals(newEndDate, contract.getEndDate());

        assertEquals(originalId, contract.getId());
    }

    @Test
    void testSetInvalidDatesThrowsException() {
        LocalDate invalidEndDate = contract.getStartDate().minusDays(5);
        Exception exceptionDate = assertThrows(IllegalArgumentException.class, () -> contract.setEndDate(invalidEndDate));
        assertEquals("Error: Start date cannot be after the end date.", exceptionDate.getMessage());

        Exception exceptionNull = assertThrows(IllegalArgumentException.class, () -> contract.setEndDate(null));
        assertEquals("Error: Dates cannot be null.", exceptionNull.getMessage());
    }

    @Test
    void testIsActive() {
        LocalDate today = LocalDate.now();

        Contract activeContract = new Contract("Artist Active", today.minusDays(5), today.plusDays(5), ContractTerms.EXCLUSIVE);
        assertTrue(activeContract.isActive());

        Contract futureContract = new Contract("Artist Future", today.plusDays(1), today.plusDays(10), ContractTerms.TEMPORARY);
        assertFalse(futureContract.isActive());

        Contract expiredContract = new Contract("Artist Expired", today.minusDays(10), today.minusDays(1), ContractTerms.PAID);
        assertFalse(expiredContract.isActive());
    }

    @Test
    void testDaysUntilExpiration() {
        assertEquals(30, contract.daysUntilExpiration());

        Contract notStarted = new Contract("Artist B", LocalDate.now().plusDays(5), LocalDate.now().plusDays(35), ContractTerms.CHARITY);
        assertEquals(30, notStarted.daysUntilExpiration());
    }

    @Test
    void testSetEndDateToNullThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> contract.setEndDate(null));
        assertEquals("Error: Dates cannot be null.", exception.getMessage());
    }

    @Test
    void testDaysUntilExpirationWhenExpired() {
        Contract expired = new Contract("Artist C", LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), ContractTerms.STANDARD);
        assertTrue(expired.daysUntilExpiration() < 0);
    }

    @Test
    void testSetInvalidTermsThrowsException() {
        Exception exceptionNull = assertThrows(IllegalArgumentException.class, () -> contract.setTerms(null));
        Assertions.assertEquals("Error: Contract terms cannot be null.", exceptionNull.getMessage());
    }
}
