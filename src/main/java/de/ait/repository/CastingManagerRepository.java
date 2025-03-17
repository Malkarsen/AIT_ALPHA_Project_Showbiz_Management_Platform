package de.ait.repository;

import de.ait.exceptions.NoRegisteredException;
import de.ait.model.Casting;
import de.ait.model.Participant;
import de.ait.utilities.ParticipantStatus;

public interface CastingManagerRepository {
    void registerCasting(Casting casting);

    Casting findCasting(String castingId);

    void showCastings();

    java.util.Map<String, Casting> getCastings();

    void registerParticipant(Participant participant) throws NoRegisteredException;

    void updateParticipantStatus(String participantId, ParticipantStatus newStatus);

    void showParticipants();
}
