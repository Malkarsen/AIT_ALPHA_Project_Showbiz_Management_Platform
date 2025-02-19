package de.ait.Model;

import de.ait.Utilities.EventType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTest {
    @Test
    void testGetArtistListWhenAllIsKnown() {
        Event event = new Event("Rock Festival 2025", EventType.CONCERT,
                LocalDate.of(2025, 7, 15), "Berlin Olympic Stadium",
                50000, 32000, 75.50,
                new HashSet<>(Arrays.asList("Metallica", "Rammstein")));
        HashSet<String> artists = event.getArtistList();
        assertEquals(2, artists.size());
        assertTrue(artists.contains("Metallica"));
        assertTrue(artists.contains("Rammstein"));
    }

    @Test
    void testGetArtistListWhenArtistListIsUnknown() {
        Event event = new Event("Rock Festival 2025", EventType.CONCERT,
                LocalDate.of(2025, 7, 15), "Berlin Olympic Stadium",
                50000, 32000, 75.50);
        HashSet<String> artists = event.getArtistList();
        assertEquals(0, artists.size());
    }

    @ParameterizedTest
    @CsvSource({
            "Rock Festival 2025, CONCERT, 2025, 7, 15, Berlin Olympic Stadium, 50000, 32000, 75.50, Metallica, Rammstein, 150_000, 2_266_000.0",
            "Champions League Final 2025, SPORTS, 2025, 5, 28, London Wembley Stadium, 90000, 87000, 60.00, Manchester City, Real Madrid, 5_220_000, 0",
            "Hamlet by Shakespeare, THEATER, 2025, 9, 10, Moscow Bolshoi Theatre, 1200, 950, 50.00, John Smith, Emma Brown, 48_000.0, -500.0",
            "Local Poetry Night, MEETUP, 2025, 3, 10, Smalltown Community Center, 100, 0, 5.00, null, null, 200, -200"

    })
    void testCalculateProfitWhenProfitIsDifferent(String name,
                                      String eventType,
                                      int year, int month, int day,
                                      String location,
                                      int totalTicketCount,
                                      int soldTicketCount,
                                      double ticketPrice,
                                      String artist1, String artist2,
                                      double expenses,
                                      double expectedProfit) {
        Event event;
        if (artist1 == null) {
            event = new Event(
                    name,                           // Название события
                    EventType.valueOf(eventType),   // Тип события
                    LocalDate.of(year, month, day), // Дата события
                    location,                       // Место проведения
                    totalTicketCount,               // Всего билетов
                    soldTicketCount,                // Продано билетов
                    ticketPrice                     // Цена билета
            );
        } else if (soldTicketCount == 0){
            event = new Event(
                    name,                           // Название события
                    EventType.valueOf(eventType),   // Тип события
                    LocalDate.of(year, month, day), // Дата события
                    location,                       // Место проведения
                    totalTicketCount,               // Всего билетов
                    ticketPrice                     // Цена билета
            );
        } else {
            event = new Event(
                    name,                           // Название события
                    EventType.valueOf(eventType),   // Тип события
                    LocalDate.of(year, month, day), // Дата события
                    location,                       // Место проведения
                    totalTicketCount,               // Всего билетов
                    soldTicketCount,                // Продано билетов
                    ticketPrice,                    // Цена билета
                    new HashSet<>(List.of(artist1, artist2)) // Список артистов
            );
        }
        double profit = event.calculateProfit(expenses); // Вычисление прибыли
        assertEquals(expectedProfit, profit);
    }
}
