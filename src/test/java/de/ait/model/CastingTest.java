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
    LocalDate newDate = LocalDate.now().plusDays(30); // Need a save result
    LocalDate castingDate = LocalDate.of(1000, 10, 10);
    private CastingManager castingManager;
    @BeforeEach
    void setUp() {
        casting = new Casting("j123", "Actor casting", "Actors casting for Matrix", castingDate, newDate);
        participant = new Participant("j123", ParticipantStatus.NEW);
        castingManager = new CastingManager();

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
        assertThrows(IllegalArgumentException.class, () -> new Casting(null, "Casting 1", "bla", castingDate, newDate));
    }

    @Test
    void constructorTestShouldReturnExceptionWithNameNull() {
        assertThrows(IllegalArgumentException.class, () -> new Casting("j123", null, "bla", castingDate, newDate));
    }

    @Test
    void constructorTestShouldReturnExceptionWithDescriptionNull() {
        assertThrows(IllegalArgumentException.class, () -> new Casting("j123", "Casting 1", null, castingDate, newDate));
    }

    @Test
    void constructorTestShouldReturnExceptionWithLocationNull() {
        assertThrows(IllegalArgumentException.class, () -> new Casting("j123", "Casting 1", "bla", null, newDate));
    }

    @Test
    void constructorTestShouldReturnExceptionWithDateNull() {
        assertThrows(IllegalArgumentException.class, () -> new Casting("j123", "Casting 1", "bla", castingDate, newDate));
    }

    @Test
    void registerParticipantTestShouldReturnCorrectlyMapSize() {

        try {
            castingManager.registerParticipant(participant);
            int size = casting.getParticipants().size();

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
// TODO переделать
    @Test
    void updateParticipantStatusTestShouldReturnTrueAndCorrectStatus() {

        try {
            castingManager.registerParticipant(participant);
            castingManager.updateParticipantStatus("j123", ParticipantStatus.IN_PROGRESS);
            ParticipantStatus newStatus = participant.getStatus();


            assertEquals(ParticipantStatus.IN_PROGRESS, newStatus);
        }
        catch (NoRegisteredException exception) {
            System.out.println("NoRegisteredException");
        }
    }
// TODO
    @Test
    void updateParticipantStatusTestShouldReturnFalse() {
        try {
            castingManager.registerParticipant(participant);
            castingManager.updateParticipantStatus(null, ParticipantStatus.IN_PROGRESS);


        }
        catch (NoRegisteredException exception) {
            System.out.println("NoRegisteredException");
        }
    }
}
