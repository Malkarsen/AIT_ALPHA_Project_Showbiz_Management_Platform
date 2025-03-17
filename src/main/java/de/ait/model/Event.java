package de.ait.model;

import de.ait.utilities.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * The Event class manages a list of artists and ticket sales.
 * It contains event information such as: name, event type,
 * date, location, total and sold ticket count,
 * ticket price for this event, and a list of artists.
 */
@Slf4j
@Getter
@Setter
public class Event {
    private final String id; // unique identifier
    private String name; // name
    private EventType eventType; // event type
    private LocalDate date; // date
    private String location; // location
    private int totalTicketCount; // total ticket count
    private int soldTicketCount; // sold ticket count
    private double ticketPrice; // price of one ticket
    HashSet<String> artistList; // list of artists (String - artist name)
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Creates a new event object when no tickets have been sold yet and the artists are unknown.
     * The unique identifier is set using UUID (Universally Unique Identifier).
     * @param name              Name
     * @param eventType         Event type
     * @param date              Date
     * @param location          Location
     * @param totalTicketCount  Total ticket count
     * @param ticketPrice       Price of one ticket
     */
    public Event(String name,
                 EventType eventType,
                 LocalDate date,
                 String location,
                 int totalTicketCount,
                 double ticketPrice) {
        String numericUUID = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        String paddedUUID = String.format("%-16s", numericUUID).replace(' ', '0');
        this.id = paddedUUID.substring(0, 16); // Generation of unique identifier
        this.name = name;
        this.eventType = eventType;
        this.date = date;
        this.location = location;
        this.totalTicketCount = totalTicketCount;
        this.soldTicketCount = 0;
        this.ticketPrice = ticketPrice;
        this.artistList = new HashSet<>();
    }

    /**
     * Creates a new event object when the number of sold tickets is known and the list of artists is unknown.
     * The unique identifier is set using UUID (Universally Unique Identifier).
     * @param name              Name
     * @param eventType         Event type
     * @param date              Date
     * @param location          Location
     * @param totalTicketCount  Total ticket count
     * @param soldTicketCount   Sold ticket count
     * @param ticketPrice       Price of one ticket
     */
    public Event(String name,
                 EventType eventType,
                 LocalDate date,
                 String location,
                 int totalTicketCount,
                 int soldTicketCount,
                 double ticketPrice) {
        String numericUUID = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        String paddedUUID = String.format("%-16s", numericUUID).replace(' ', '0');
        this.id = paddedUUID.substring(0, 16); // Generation of unique identifier
        this.name = name;
        this.eventType = eventType;
        this.date = date;
        this.location = location;
        this.totalTicketCount = totalTicketCount;
        this.soldTicketCount = soldTicketCount;
        this.ticketPrice = ticketPrice;
        this.artistList = new HashSet<>();
    }

    /**
     * Creates a new event object when all data is known.
     * The unique identifier is set using UUID (Universally Unique Identifier).
     * @param name              Name
     * @param eventType         Event type
     * @param date              Date
     * @param location          Location
     * @param totalTicketCount  Total ticket count
     * @param soldTicketCount   Sold ticket count
     * @param ticketPrice       Price of one ticket
     * @param artistList        List of artists
     */
    public Event(String name,
                 EventType eventType,
                 LocalDate date,
                 String location,
                 int totalTicketCount,
                 int soldTicketCount,
                 double ticketPrice,
                 HashSet<String> artistList) {
        String numericUUID = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        String paddedUUID = String.format("%-16s", numericUUID).replace(' ', '0');
        this.id = paddedUUID.substring(0, 16); // Generation of unique identifier
        this.name = name;
        this.eventType = eventType;
        this.date = date;
        this.location = location;
        this.totalTicketCount = totalTicketCount;
        this.soldTicketCount = soldTicketCount;
        this.ticketPrice = ticketPrice;
        this.artistList = artistList;
    }

    /**
     * Returns a copy of the artist list.
     * @return Copy of the artist list
     */
    public HashSet<String> getArtistList() {
        return new HashSet<>(artistList);
    }

    /**
     * Method for selling tickets.
     * @param count Number of tickets to sell.
     */
    public void sellTicket(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("The number of tickets must be greater than 0.");
        }
        if (soldTicketCount + count > totalTicketCount) {
            throw new IllegalArgumentException("Not enough tickets. Only " + (totalTicketCount - soldTicketCount) + " available.");
        }
        soldTicketCount += count;
        System.out.println(count + " tickets sold. Tickets remaining: " + (totalTicketCount - soldTicketCount));
    }

    /**
     * Method for displaying ticket information.
     */
    public void printTicketInfo() {
        System.out.println("Event: " + name);
        System.out.println("Total tickets: " + totalTicketCount);
        System.out.println("Tickets sold: " + soldTicketCount);
        System.out.println("Tickets remaining: " + (totalTicketCount - soldTicketCount));
    }

    /**
     * Method for adding an artist to the event.
     * @param artistName Artist name.
     */
    public void addArtist(String artistName) {
        if (artistName == null || artistName.trim().isEmpty()) {
            throw new IllegalArgumentException("Artist name cannot be empty.");
        }
        if (!artistList.add(artistName)) {
            throw new IllegalArgumentException("Artist is already added to the event.");
        }
        System.out.println("Artist " + artistName + " added to the event: " + name);
    }

    /**
     * Method for removing an artist from the event.
     * @param artistName Artist name.
     */
    public void removeArtist(String artistName) {
        if (!artistList.contains(artistName)) {
            throw new NoSuchElementException("Artist not found in the event.");
        }
        artistList.remove(artistName);
        System.out.println("Artist " + artistName + " removed from the event: " + name);
    }

    /**
     * Method for calculating profit based on ticket price and expenses.
     * @param expenses   Expenses
     * @return           Net profit (negative value indicates a loss)
     */
    public double calculateProfit(double expenses) {
        double totalIncome = soldTicketCount * ticketPrice; // Total income from ticket sales
        double profit = totalIncome - expenses;
        if (profit > 0) {
            System.out.println("Event " + name + " made a profit of: " + profit);
            log.info("Event {} made a profit of: {}", name, profit);
        } else if (profit == 0) {
            System.out.println("Event " + name + " broke even.");
            log.info("Event {} broke even.", name);
        } else {
            System.out.println("Event " + name + " incurred a loss of: " + Math.abs(profit));
            log.info("Event {} incurred a loss of: {}", name, Math.abs(profit));
        }
        return profit;
    }

    /**
     * Method for displaying all event information.
     */
    public void printEventInfo() {
        System.out.println("Identifier: " + id);
        System.out.println("Name: " + name);
        System.out.println("Event type: " + eventType);
        System.out.println("Date: " + date.format(dateFormatter));
        System.out.println("Location: " + location);
        System.out.println("Total tickets: " + totalTicketCount);
        System.out.println("Tickets sold: " + soldTicketCount);
        System.out.println("Tickets remaining: " + (totalTicketCount - soldTicketCount));
        if (artistList.isEmpty()) {
            System.out.println("Artist list: empty");
        } else {
            System.out.println("Artist list: " + artistList);
        }
    }
}