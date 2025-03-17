package de.ait.model;

import de.ait.utilities.ParticipantStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParticipantTest {
    @Test
    void constructorTest() {

        Participant participant = new Participant("Anton", ParticipantStatus.NEW);


        String name = participant.getName();
        ParticipantStatus status = participant.getStatus();

        assertEquals("Anton", name);
        assertEquals(ParticipantStatus.NEW, status);
    }

    @Test
    void constructorTestShouldReturnExceptionWithIdNull() {
        assertThrows(IllegalArgumentException.class, () -> new Participant(null, ParticipantStatus.NEW));
    }

    @Test
    void constructorTestShouldReturnExceptionWithNameNull() {
        assertThrows(IllegalArgumentException.class, () -> new Participant("j123", null));
    }

    @Test
    void constructorTestShouldReturnExceptionWithStatusNull() {
        assertThrows(IllegalArgumentException.class, () -> new Participant("j123", null));
    }
}

