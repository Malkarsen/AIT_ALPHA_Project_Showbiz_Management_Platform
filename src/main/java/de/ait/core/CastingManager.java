package de.ait.core;

import de.ait.model.Casting;

public interface CastingManager {
    void registerCasting(Casting casting);

    Casting findCasting(String castingId);

    void showCastings();

    java.util.Map<String, Casting> getCastings();
}
