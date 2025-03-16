package de.ait.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {

    private Contract contract;

    @BeforeEach
    void setUp() {
        // Initialize a contract instance before each test
        contract = new Contract("Artist A", LocalDate.now(), LocalDate.now().plusDays(30), "Standard Terms");
    }

    @Test
    void testContractCreation() {
        // Ensure the contract is created with valid parameters
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(30);
        assertNotNull(contract.getId(), "The ID must be generated");
        assertEquals("Artist A", contract.getArtistName(), "The artist's name must match");
        assertEquals(startDate, contract.getStartDate(), "The start date must coincide");
        assertEquals(endDate, contract.getEndDate(), "The end date must coincide");
        assertEquals("Standard Terms", contract.getTerms(), "The terms must match");
    }

    @Test
    void testSetValidArtistName() {
        // Test updating the artist's name
        String originalId = contract.getId();
        String originalTerms = contract.getTerms();
        contract.setArtistName("Artist B");
        assertEquals("Artist B", contract.getArtistName(), "The artist's name should update");
        assertEquals(originalId, contract.getId(), "The ID should not change");
        assertEquals(originalTerms, contract.getTerms(), "Terms should not change");
    }

    @Test
    void testSetInvalidArtistNameThrowsException() {
        // Ensure setting an invalid artist name throws an exception
        Exception exceptionEmpty = assertThrows(IllegalArgumentException.class, () -> contract.setArtistName(""));
        assertEquals("Error: Artist name cannot be empty.", exceptionEmpty.getMessage());

        Exception exceptionNull = assertThrows(IllegalArgumentException.class, () -> contract.setArtistName(null));
        assertEquals("Error: Artist name cannot be empty.", exceptionNull.getMessage());
    }

    @Test
    void testSetValidDates() {
        // Ensure updating dates with valid values works
        String originalId = contract.getId();
        LocalDate newStartDate = LocalDate.now().plusDays(5);
        LocalDate newEndDate = LocalDate.now().plusDays(60);

        contract.setStartDate(newStartDate);
        assertEquals(newStartDate, contract.getStartDate(), "The start date should be updated");

        contract.setEndDate(newEndDate);
        assertEquals(newEndDate, contract.getEndDate(), "The end date should update");

        assertEquals(originalId, contract.getId(), "The ID should not change");
    }

    @Test
    void testSetInvalidDatesThrowsException() {
        // Ensure setting an invalid date range throws an exception
        LocalDate invalidEndDate = contract.getStartDate().minusDays(5);
        Exception exceptionDate = assertThrows(IllegalArgumentException.class, () -> contract.setEndDate(invalidEndDate));
        assertEquals("Error: Start date cannot be after the end date.", exceptionDate.getMessage());

        Exception exceptionNull = assertThrows(IllegalArgumentException.class, () -> contract.setEndDate(null));
        assertEquals("Error: Dates cannot be null.", exceptionNull.getMessage());
    }

    @Test
    void testIsActive() {
        // Check if the contract is active
        assertTrue(contract.isActive(), "The contract must be active");

        Contract notStarted = new Contract("Artist B", LocalDate.now().plusDays(5), LocalDate.now().plusDays(35), "Terms");
        assertFalse(notStarted.isActive(), "The contract must not be active before the start date");

        Contract expired = new Contract("Artist C", LocalDate.now().minusDays(10), LocalDate.now().minusDays(1), "Terms");
        assertFalse(expired.isActive(), "The contract should not be active after the end date");
    }

    @Test
    void testDaysUntilExpiration() {
        // Verify the number of days until contract expiration
        assertEquals(30, contract.daysUntilExpiration(), "The days to expiration must be 30");

        Contract notStarted = new Contract("Artist B", LocalDate.now().plusDays(5), LocalDate.now().plusDays(35), "Terms");
        assertEquals(30, notStarted.daysUntilExpiration(), "The days from startDate to endDate must be 30 days");
    }

    @Test
    void testSetEndDateToNullThrowsException() {
        // Checking null via throwing an exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> contract.setEndDate(null));
        assertEquals("Error: Dates cannot be null.", exception.getMessage());
    }

    @Test
    void testDaysUntilExpirationWhenExpired() {
        Contract expired = new Contract("Artist C", LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), "Terms");
        assertTrue(expired.daysUntilExpiration() < 0, "Days to expiration must be negative for an expired contract");
    }

    @Test
    void testSetInvalidTermsThrowsException() {
        // Ensure setting empty contract terms throws an exception
        Exception exceptionEmpty = assertThrows(IllegalArgumentException.class, () -> contract.setTerms(""));
        assertEquals("Error: Contract terms cannot be empty.", exceptionEmpty.getMessage());

        Exception exceptionNull = assertThrows(IllegalArgumentException.class, () -> contract.setTerms(null));
        assertEquals("Error: Contract terms cannot be empty.", exceptionNull.getMessage());
    }
}
