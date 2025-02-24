package de.ait.model;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * The Contract class represents an agreement with an artist.
 * It contains contract details, including the artist's name, start and end dates,
 * contract terms, and a unique contract identifier.
 */
@Slf4j
public class Contract {
    private final String id; // Unique identifier, final because it does not change
    private String artistName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String terms;

    /**
     * Constructor for creating a new contract.
     *
     * @param artistName name of the artist.
     * @param startDate  contract start date.
     * @param endDate    contract end date.
     * @param terms      contract terms.
     */
    public Contract(String artistName, LocalDate startDate, LocalDate endDate, String terms) {
        validateArtistName(artistName);
        validateDates(startDate, endDate);
        validateTerms(terms);

        this.id = UUID.randomUUID().toString(); // Generate a unique identifier
        this.artistName = artistName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.terms = terms;

        log.info("New contract created: {}", this);
    }

    /**
     * Get the unique contract identifier.
     *
     * @return contract ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Get the artist's name.
     *
     * @return artist name.
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * Set a new artist name.
     *
     * @param artistName new artist name.
     */
    public void setArtistName(String artistName) {
        validateArtistName(artistName);
        this.artistName = artistName;
        log.info("Updated artist name for contract {}: {}", id, artistName);
    }

    /**
     * Get the contract start date.
     *
     * @return start date.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Set a new contract start date.
     *
     * @param startDate new start date.
     */
    public void setStartDate(LocalDate startDate) {
        validateDates(startDate, this.endDate);
        this.startDate = startDate;
        log.info("Updated start date for contract {}: {}", id, startDate);
    }

    /**
     * Get the contract end date.
     *
     * @return end date.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Set a new contract end date.
     *
     * @param endDate new end date.
     */
    public void setEndDate(LocalDate endDate) {
        validateDates(this.startDate, endDate);
        this.endDate = endDate;
        log.info("Updated end date for contract {}: {}", id, endDate);
    }

    /**
     * Get the contract terms.
     *
     * @return contract terms.
     */
    public String getTerms() {
        return terms;
    }

    /**
     * Set new contract terms.
     *
     * @param terms new contract terms.
     */
    public void setTerms(String terms) {
        validateTerms(terms);
        this.terms = terms;
        log.info("Updated terms for contract {}: {}", id, terms);
    }

    // *** Validation Methods ***
    private void validateArtistName(String artistName) {
        if (artistName == null || artistName.trim().isEmpty()) {
            log.error("Validation error: Artist name cannot be empty.");
            throw new IllegalArgumentException("Error: Artist name cannot be empty.");
        }
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            log.error("Validation error: Dates cannot be null.");
            throw new IllegalArgumentException("Error: Dates cannot be null.");
        }
        if (startDate.isAfter(endDate)) {
            log.error("Validation error: Start date cannot be after the end date.");
            throw new IllegalArgumentException("Error: Start date cannot be after the end date.");
        }
    }

    private void validateTerms(String terms) {
        if (terms == null || terms.trim().isEmpty()) {
            log.error("Validation error: Contract terms cannot be empty.");
            throw new IllegalArgumentException("Error: Contract terms cannot be empty.");
        }
    }

    /**
     * Checks if the contract is currently active.
     *
     * @return true if the contract is active, otherwise false.
     */
    public boolean isActive() {
        if (startDate == null || endDate == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return !today.isBefore(startDate) && !today.isAfter(endDate);
    }

    /**
     * Calculates the number of days until the contract expires.
     *
     * @return the number of days until expiration.
     */
    public long daysUntilExpiration() {
        if (endDate == null) {
            return -1; // Error if the date is not set
        }
        LocalDate today = LocalDate.now();
        LocalDate start = this.startDate;

        // If the contract has not started yet, count from the start date instead of today
        LocalDate referenceDate = today.isBefore(start) ? start : today;

        return ChronoUnit.DAYS.between(referenceDate, endDate);
    }

    /**
     * Returns a string representation of the contract.
     *
     * @return contract details as a string.
     */
    @Override
    public String toString() {
        return "Contract{" +
                "id='" + id + '\'' +
                ", artistName='" + artistName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", terms='" + terms + '\'' +
                '}';
    }
}