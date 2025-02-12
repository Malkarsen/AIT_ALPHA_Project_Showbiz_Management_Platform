package de.ait;

import de.ait.Core.CastingManager;
import de.ait.Model.Casting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CastingManagerTest {

    private CastingManager manager;
    private Casting casting;

    @BeforeEach
    void setUp() {
        manager = new CastingManager();
        casting = new Casting("j123", "Actor casting", "Actors casting for Matrix", "Hollywood", LocalDate.now().plusDays(30));
    }

    @Test
    void registerCastingTestShouldReturnCorrectMapSize() {

        manager.registerCasting(casting);
        int size = manager.getCastings().size();

        assertEquals(1, size);
    }

    @Test
    void findCastingTestShouldReturnCorrectCasting() {

        manager.registerCasting(casting);
        Casting findCasting = manager.findCasting("j123");

        assertNotNull(findCasting);
    }
}
