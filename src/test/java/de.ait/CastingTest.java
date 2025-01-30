package de.ait;

import de.ait.Model.Casting;
import de.ait.Model.Participant;
import de.ait.Model.ParticipantStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CastingTest {

    private Casting casting;
    private Participant participant;

    @BeforeEach
    void setUp() {
        casting = new Casting("j123", "Actor casting", "Actors casting for Matrix", "Hollywood");
        participant = new Participant("j123", "Anton", ParticipantStatus.NEW);
    }

    @Test
    void constructorTest() {

        String id = casting.getId();
        String name = casting.getName();
        String descr = casting.getDescription();
        String loc = casting.getLocation();

        assertEquals("j123", id);
        assertEquals("Actor casting", name);
        assertEquals("Actors casting for Matrix", descr);
        assertEquals("Hollywood", loc);
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
}
