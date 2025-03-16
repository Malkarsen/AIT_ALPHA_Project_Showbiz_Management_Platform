package de.ait.service;

import de.ait.exceptions.EventAlreadyInListException;
import de.ait.exceptions.EventIsNotInListException;
import de.ait.model.Event;
import de.ait.utilities.EventType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventManagerTest {

    @Test
    void testGetEventByIdIsSuccess() {
        Event event = new Event("Local Chess Championship", EventType.ESPORTS,
                LocalDate.of(2025, 11, 5), "Moscow Chess Club",
                100, 20, 15.00, new HashSet<>(Set.of("Gap Burlington")));
        EventManager eventManager = new EventManager();
        String eventId = "null";
        try {
            eventId = eventManager.addEvent(event);
        } catch (EventAlreadyInListException exception) {
            System.out.println("Event is already in the list " + exception.getMessage());
        }
        String finalEventId = eventId;
        assertDoesNotThrow(() -> eventManager.getEventById(finalEventId));
    }

    @Test
    void testGetEventByIdFailWhenEventIsNotInList() {
        EventManager eventManager = new EventManager();
        String eventId = "null";
        assertThrows(EventIsNotInListException.class, () -> eventManager.getEventById(eventId));
    }

    @ParameterizedTest
    @CsvSource({
            "Rock Festival 2025, CONCERT, 2025, 7, 15, Berlin Olympic Stadium, 50000, 32000, 75.50, Metallica",
            "Champions League Final 2025, SPORTS, 2025, 5, 28, London Wembley Stadium, 90000, 87000, 60.00, Manchester City",
            "Hamlet by Shakespeare, THEATER, 2025, 9, 10, Moscow Bolshoi Theatre, 1200, 950, 50.00, John Smith",
            "Local Poetry Night, MEETUP, 2025, 3, 10, Smalltown Community Center, 100, 0, 5.00, Emma Brown",
            "World Cup 2025, SPORTS, 2025, 7, 15, Russia Stadium, 300000, 250000, 70.00, Real Madrid"
    })
    void testAddEventIsSuccess(String name,
                               String eventType,
                               int year, int month, int day,
                               String location,
                               int totalTicketCount,
                               int soldTicketCount,
                               double ticketPrice,
                               String artist) {
        Event event = new Event(
                name,                           // Event name
                EventType.valueOf(eventType),   // Event type
                LocalDate.of(year, month, day), // Event date
                location,                       // Location
                totalTicketCount,               // Total tickets
                soldTicketCount,                // Sold tickets
                ticketPrice,                    // Ticket price
                new HashSet<>(List.of(artist))  // List of artists
        );
        EventManager eventManager = new EventManager();
        assertDoesNotThrow(() -> eventManager.addEvent(event));
    }

    @Test
    void testAddEventIsFailWhenEventIsNull() {
        Event event = null;
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.addEvent(event));
    }

    @Test
    void testAddEventIsFailWhenNameIsNull() {
        Event event = new Event(null, EventType.CONCERT,
                LocalDate.of(2025, 7, 15), "Berlin Olympic Stadium",
                50000, 32000, 75.50, new HashSet<>(Set.of("Metallica")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.addEvent(event));
    }

    @Test
    void testAddEventIsFailWhenNameIsEmpty() {
        Event event = new Event("", EventType.SPORTS,
                LocalDate.of(2025, 5, 28), "London Wembley Stadium",
                90000, 87000, 60.00, new HashSet<>(Set.of("Manchester City")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.addEvent(event));
    }

    @Test
    void testAddEventIsFailWhenEventTypeIsNull() {
        Event event = new Event("Hamlet by Shakespeare", null,
                LocalDate.of(2025, 9, 10), "Moscow Bolshoi Theatre",
                1200, 950, 50.00, new HashSet<>(Set.of("John Smith")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.addEvent(event));
    }

    @Test
    void testAddEventIsFailWhenDateIsNull() {
        Event event = new Event("Local Poetry Night", EventType.MEETUP,
                null, "Smalltown Community Center",
                100, 0, 5.00, new HashSet<>(Set.of("Emma Brown")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.addEvent(event));
    }

    @Test
    void testAddEventIsFailWhenLocationIsNull() {
        Event event = new Event("Local Farmers Market", EventType.OPEN_AIR,
                LocalDate.of(2025, 4, 20), null,
                500, 0, 0.01, new HashSet<>(Set.of("Bob")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.addEvent(event));
    }

    @Test
    void testAddEventIsFailWhenLocationIsEmpty() {
        Event event = new Event("World Cup 2025", EventType.SPORTS,
                LocalDate.of(2025, 7, 15), "",
                300000, 250000, 70.00, new HashSet<>(Set.of("Real Madrid")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.addEvent(event));
    }

    @Test
    void testAddEventIsFailWhenTotalTicketCountIsIncorrect() {
        Event event = new Event("Indie Book Fair", EventType.EXHIBITION,
                LocalDate.of(2025, 9, 12), "Boston Convention Center",
                0, 300, 10.00, new HashSet<>(Set.of("Dadly Born")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.addEvent(event));
    }

    @Test
    void testAddEventIsFailWhenTicketPriceIsIncorrect() {
        Event event = new Event("National Art Fair 2025", EventType.FESTIVAL,
                LocalDate.of(2025, 10, 15), "National Gallery",
                15000, 8000, -50.00, new HashSet<>(Set.of("Brown Qu")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.addEvent(event));
    }

    @Test
    void testAddEventIsFailWhenEventIsYetInList() {
        Event event = new Event("Street Jazz Night", EventType.CONCERT,
                LocalDate.of(2025, 8, 10), "New Orleans French Quarter",
                300, 0, 25.00, new HashSet<>(Set.of("Peet Place")));
        EventManager eventManager = new EventManager();
        try {
            eventManager.addEvent(event);
        } catch (EventAlreadyInListException exception) {
            System.out.println("Event is already in the list " + exception.getMessage());
        }
        assertThrows(EventAlreadyInListException.class, () -> eventManager.addEvent(event));
    }

    @ParameterizedTest
    @CsvSource({
            "Rock Festival 2025, CONCERT, 2025, 7, 15, Berlin Olympic Stadium, 50000, 32000, 75.50, Metallica",
            "Champions League Final 2025, SPORTS, 2025, 5, 28, London Wembley Stadium, 90000, 87000, 60.00, Manchester City",
            "Hamlet by Shakespeare, THEATER, 2025, 9, 10, Moscow Bolshoi Theatre, 1200, 950, 50.00, John Smith",
            "Local Poetry Night, MEETUP, 2025, 3, 10, Smalltown Community Center, 100, 0, 5.00, Emma Brown",
            "World Cup 2025, SPORTS, 2025, 7, 15, Russia Stadium, 300000, 250000, 70.00, Real Madrid"
    })
    void testRemoveEventIsSuccess(String name,
                                  String eventType,
                                  int year, int month, int day,
                                  String location,
                                  int totalTicketCount,
                                  int soldTicketCount,
                                  double ticketPrice,
                                  String artist) {
        Event event = new Event(
                name,                           // Event name
                EventType.valueOf(eventType),   // Event type
                LocalDate.of(year, month, day), // Event date
                location,                       // Location
                totalTicketCount,               // Total tickets
                soldTicketCount,                // Sold tickets
                ticketPrice,                    // Ticket price
                new HashSet<>(List.of(artist))  // List of artists
        );
        EventManager eventManager = new EventManager();
        try {
            eventManager.addEvent(event);
        } catch (EventAlreadyInListException exception) {
            System.out.println("Event is already in the list " + exception.getMessage());
        }
        assertDoesNotThrow(() -> eventManager.removeEvent(event));
    }

    @Test
    void testRemoveEventIsFailWhenEventIsNull() {
        Event event = null;
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.removeEvent(event));
    }

    @Test
    void testRemoveEventIsFailWhenNameIsNull() {
        Event event = new Event(null, EventType.CONCERT,
                LocalDate.of(2025, 7, 15), "Berlin Olympic Stadium",
                50000, 32000, 75.50, new HashSet<>(Set.of("Metallica")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.removeEvent(event));
    }

    @Test
    void testRemoveEventIsFailWhenNameIsEmpty() {
        Event event = new Event("", EventType.SPORTS,
                LocalDate.of(2025, 5, 28), "London Wembley Stadium",
                90000, 87000, 60.00, new HashSet<>(Set.of("Manchester City")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.removeEvent(event));
    }

    @Test
    void testRemoveEventIsFailWhenEventTypeIsNull() {
        Event event = new Event("Hamlet by Shakespeare", null,
                LocalDate.of(2025, 9, 10), "Moscow Bolshoi Theatre",
                1200, 950, 50.00, new HashSet<>(Set.of("John Smith")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.removeEvent(event));
    }

    @Test
    void testRemoveEventIsFailWhenDateIsNull() {
        Event event = new Event("Local Poetry Night", EventType.MEETUP,
                null, "Smalltown Community Center",
                100, 0, 5.00, new HashSet<>(Set.of("Emma Brown")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.removeEvent(event));
    }

    @Test
    void testRemoveEventIsFailWhenLocationIsNull() {
        Event event = new Event("Local Farmers Market", EventType.OPEN_AIR,
                LocalDate.of(2025, 4, 20), null,
                500, 0, 0.01, new HashSet<>(Set.of("Bob")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.removeEvent(event));
    }

    @Test
    void testRemoveEventIsFailWhenLocationIsEmpty() {
        Event event = new Event("World Cup 2025", EventType.SPORTS,
                LocalDate.of(2025, 7, 15), "",
                300000, 250000, 70.00, new HashSet<>(Set.of("Real Madrid")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.removeEvent(event));
    }

    @Test
    void testRemoveEventIsFailWhenTotalTicketCountIsIncorrect() {
        Event event = new Event("Indie Book Fair", EventType.EXHIBITION,
                LocalDate.of(2025, 9, 12), "Boston Convention Center",
                0, 300, 10.00, new HashSet<>(Set.of("Dadly Born")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.removeEvent(event));
    }

    @Test
    void testRemoveEventIsFailWhenTicketPriceIsIncorrect() {
        Event event = new Event("National Art Fair 2025", EventType.FESTIVAL,
                LocalDate.of(2025, 10, 15), "National Gallery",
                15000, 8000, -50.00, new HashSet<>(Set.of("Brown Qu")));
        EventManager eventManager = new EventManager();
        assertThrows(IllegalArgumentException.class, () -> eventManager.removeEvent(event));
    }

    @Test
    void testRemoveEventIsFailWhenEventIsNotInList() {
        Event event = new Event("Street Jazz Night", EventType.CONCERT,
                LocalDate.of(2025, 8, 10), "New Orleans French Quarter",
                300, 0, 25.00, new HashSet<>(Set.of("Peet Place")));
        EventManager eventManager = new EventManager();
        assertThrows(EventIsNotInListException.class, () -> eventManager.removeEvent(event));
    }

    @Test
    void testRemoveEventByIdIsSuccess() {
        Event event = new Event("Pop Festival 2025", EventType.FESTIVAL,
                LocalDate.of(2025, 10, 20), "Moscow Theatre",
                100000, 24000, 50.00);
        EventManager eventManager = new EventManager();
        String eventId = null;
        try {
            eventId = eventManager.addEvent(event);
        } catch (EventAlreadyInListException exception) {
            System.out.println("Event is already in the list " + exception.getMessage());
        }
        String finalEventId = eventId;
        assertDoesNotThrow(() -> eventManager.removeEventById(finalEventId));
    }

    @Test
    void testRemoveEventByIdFailWhenEventIsNotInList() {
        EventManager eventManager = new EventManager();
        String eventId = "null";
        assertThrows(EventIsNotInListException.class, () -> eventManager.removeEventById(eventId));
    }
}