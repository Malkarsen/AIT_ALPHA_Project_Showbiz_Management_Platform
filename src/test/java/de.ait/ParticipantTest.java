package de.ait;

import de.ait.Model.Participant;
import de.ait.Model.ParticipantStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParticipantTest {
    @Test
    void constructorTest() {
        Participant participant = new Participant("j123", "Anton", ParticipantStatus.NEW);

        String id = participant.getId();
        String name = participant.getName();
        ParticipantStatus status = participant.getStatus();

        assertEquals("j123", id);
        assertEquals("Anton", name);
        assertEquals(ParticipantStatus.NEW, status);
    }
}
