package de.ait.service;

import de.ait.model.Casting;
import de.ait.repository.CastingManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CastingManagerTest {

    private CastingManager manager;
    private Casting casting;
    LocalDate castingDate = LocalDate.of(1000, 10, 10);

    @BeforeEach
    void setUp() {
        manager = new CastingManager();
        casting = new Casting( "Actor casting", "Actors casting for Matrix", "Hollywood", castingDate);
    }

    @Test
    void registerCastingTestShouldReturnCorrectMapSize() {

        manager.registerCasting(casting);
        int size = manager.getCastings().size();

        assertEquals(1, size);
    }
}
