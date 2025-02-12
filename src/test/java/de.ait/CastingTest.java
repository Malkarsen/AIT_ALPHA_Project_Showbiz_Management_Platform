package de.ait;

import de.ait.Model.Casting;
import de.ait.Model.Participant;
import de.ait.Utilities.ParticipantStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CastingTest {

    private Casting casting;
    private Participant participant;

    @BeforeEach
    void setUp() {
        casting = new Casting("j123", "Actor casting", "Actors casting for Matrix", "Hollywood", LocalDate.now().plusDays(30));
        participant = new Participant("j123", "Anton", ParticipantStatus.NEW);
    }

    @Test
    void constructorTest() {

        String id = casting.getId();
        String name = casting.getName();
        String descr = casting.getDescription();
        String loc = casting.getLocation();
        LocalDate date = casting.getCastingDate();

        assertEquals("j123", id);
        assertEquals("Actor casting", name);
        assertEquals("Actors casting for Matrix", descr);
        assertEquals("Hollywood", loc);
        assertEquals(LocalDate.now().plusDays(30), date);
    }

    @Test
    void constructorTestShouldReturnExceptionWithIdNull() {
        assertThrows(IllegalArgumentException.class, () -> new Casting(null, "Casting 1", "bla", "bla", LocalDate.now().plusDays(30)));
    }

    @Test
    void constructorTestShouldReturnExceptionWithNameNull() {
        assertThrows(IllegalArgumentException.class, () -> new Casting("j123", null, "bla", "bla", LocalDate.now().plusDays(30)));
    }

    @Test
    void constructorTestShouldReturnExceptionWithDescriptionNull() {
        assertThrows(IllegalArgumentException.class, () -> new Casting("j123", "Casting 1", null, "bla", LocalDate.now().plusDays(30)));
    }

    @Test
    void constructorTestShouldReturnExceptionWithLocationNull() {
        assertThrows(IllegalArgumentException.class, () -> new Casting("j123", "Casting 1", "bla", null, LocalDate.now().plusDays(30)));
    }

    @Test
    void constructorTestShouldReturnExceptionWithDateNull() {
        assertThrows(IllegalArgumentException.class, () -> new Casting("j123", "Casting 1", "bla", "bla", null));
    }

    @Test
    void registerParticipantTestShouldReturnCorrectlyMapSize() {

        casting.registerParticipant(participant);
        int size = casting.getParticipants().size();

        assertEquals(1, size);
    }

    @Test
    void updateParticipantStatusTestShouldReturnTrueAndCorrectStatus() {

        casting.registerParticipant(participant);
        boolean updateStatus = casting.updateParticipantStatus("j123", ParticipantStatus.IN_PROGRESS);
        ParticipantStatus newStatus = participant.getStatus();

        assertTrue(updateStatus);
        assertEquals(ParticipantStatus.IN_PROGRESS, newStatus);
    }

    @Test
    void updateParticipantStatusTestShouldReturnFalse() {

        casting.registerParticipant(participant);
        boolean updateStatus = casting.updateParticipantStatus(null, ParticipantStatus.IN_PROGRESS);

        assertFalse(updateStatus);
    }
}
