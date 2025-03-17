package de.ait.model;

import de.ait.exceptions.NoRegisteredException;
import de.ait.service.CastingManager;
import de.ait.utilities.ParticipantStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CastingTest {

    private Casting casting;
    private Participant participant;
    LocalDate castingDate = LocalDate.of(1000, 10, 10);
    private CastingManager castingManager;
    @BeforeEach
    void setUp() {
        casting = new Casting( "Actor casting", "Actors casting for Matrix", "Hollywood", castingDate);
        participant = new Participant("j123", ParticipantStatus.NEW);
        castingManager = new CastingManager();

    }

    @Test
    void constructorTest() {

        String name = casting.getName();
        String descr = casting.getDescription();
        String loc = casting.getLocation();

        assertEquals("Actor casting", name);
        assertEquals("Actors casting for Matrix", descr);
        assertEquals("Hollywood", loc);
    }



    @Test
    void constructorTestShouldReturnExceptionWithNameNull() {
        assertThrows(IllegalArgumentException.class, () -> new Casting(null, "bla", "bla", castingDate));
    }

    @Test
    void constructorTestShouldReturnExceptionWithDescriptionNull() {
        assertThrows(IllegalArgumentException.class, () -> new Casting("Casting 1", "bla", null, castingDate));
    }

    @Test
    void constructorTestShouldReturnExceptionWithLocationNull() {
        assertThrows(IllegalArgumentException.class, () -> new Casting("Casting 1", "bla", "bla", null));
    }

    @Test
    void registerParticipantTestShouldReturnCorrectlyMapSize() {

        try {
            castingManager.registerParticipant(participant);
            int size = castingManager.getParticipants().size();

            assertEquals(1, size);
        }
        catch (NoRegisteredException exception) {
            System.out.println("NoRegisteredException");
        }
    }

    @Test
    void registerParticipantTestShouldReturnExceptionWithParticipantNull() {
        assertThrows(NoRegisteredException.class, () -> castingManager.registerParticipant(null));
    }

// TODO
    @Test
    void updateParticipantStatusTestShouldReturnFalse() {
        try {
            castingManager.registerParticipant(participant);
            castingManager.updateParticipantStatus(null, ParticipantStatus.IN_PROGRESS);
        }
        catch (IllegalArgumentException | NoRegisteredException exception) {
            System.out.println("NoRegisteredException");
        }
    }
}
