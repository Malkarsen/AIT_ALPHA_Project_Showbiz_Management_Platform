package de.ait.repository;

import de.ait.model.Casting;

public interface CastingManagerRepository {
    void registerCasting(Casting casting);

    Casting findCasting(String castingId);

    void showCastings();

    java.util.Map<String, Casting> getCastings();
}
