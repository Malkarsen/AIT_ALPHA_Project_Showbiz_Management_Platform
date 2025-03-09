package de.ait.app;

import de.ait.core.EventManager;
import de.ait.exceptions.EventAlreadyInListException;
import de.ait.exceptions.EventIsNotInListException;
import de.ait.model.Event;
import de.ait.utilities.EventType;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Scanner;

@Slf4j
public class EventManagerApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EventManager eventManager = new EventManager();

    public static void main(String[] args) {
        byte choice;
        boolean run = true;
        while (run) {
            showMenu();
            choice = inputChoice();

            switch (choice) {
                case 1 -> addEvent(); // Add a new Event
                case 2 -> deleteEvent(); // Delete an Event by ID
                case 3 -> {
                    // Working with an Event
                    if (!eventManager.getEvents().isEmpty()) {
                        boolean runEvent = true;
                        byte choiceEvent;

                        String eventId = inputEventId();
                        Event selectedEvent = findEventById(eventId);
                        if (selectedEvent != null) {
                            while (runEvent) {
                                showEventMenu();r
                                choiceEvent = inputChoice();

                                switch (choiceEvent) {
                                    case 1 -> printArtistsList(selectedEvent); // Display a list of artists at the event
                                    case 2 -> {
                                        // Sell tickets
                                        // TODO Artur Begichev
                                    }
                                    case 3 -> {
                                        // Displaying ticket information
                                        // TODO Artur Begichev
                                    }
                                    case 4 -> {
                                        // Add a new artist
                                        // TODO Artur Begichev
                                    }
                                    case 5 -> {
                                        // Delete an artist
                                        // TODO Artur Begichev
                                    }
                                    case 6 -> calculateEventProfit(selectedEvent); // Calculate the profit
                                    case 7 -> selectedEvent.printEventInfo(); // Display all event information
                                    case 8 -> {
                                        runEvent = false;
                                        System.out.println("Exit the Event menu.");
                                        log.info("Exit the Event menu.");
                                    }
                                    default -> {
                                        System.out.println("Invalid choice in Event menu. Please try again.");
                                        log.warn("Invalid choice in Event menu. Please try again.");
                                    }
                                }
                            }
                        } else {
                            System.out.println("Event with id " + eventId + " not found.");
                            log.warn("Event with id {} not found.", eventId);
                        }
                    } else {
                        System.out.println("No events found for working.");
                        log.warn("No events found for working.");
                    }
                }
                case 4 -> eventManager.displayAllEvents(); // Display all Events
                case 5 -> {
                    run = false;
                    System.out.println("Exiting the program.");
                    log.warn("Exiting the program.");
                }
                default -> {
                    System.out.println("Invalid choice in main menu. Please try again.");
                    log.warn("Invalid choice in main menu. Please try again.");
                }
            }
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("Menu: ");
        System.out.println("1. Add a new Event");
        System.out.println("2. Delete an Event by ID");
        System.out.println("3. Working with an Event");
        System.out.println("4. Display all Events");
        System.out.println("5. Exit");
        System.out.print("Choose an action: ");
    }

    private static void showEventMenu() {
        System.out.println("1. Display a list of artists at the event");
        System.out.println("2. Sell tickets");
        System.out.println("3. Displaying ticket information");
        System.out.println("4. Add a new artist");
        System.out.println("5. Delete an artist");
        System.out.println("6. Calculate the profit");
        System.out.println("7. Display all event information");
        System.out.println("8. Return to the main menu");
        System.out.print("Choose an action: ");
    }

    private static byte inputChoice() {
        byte choice = scanner.nextByte();
        scanner.nextLine();
        return choice;
    }

    private static Event buildEvent() {
        System.out.print("Enter Event name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Event type (CONCERT,SPORTS,THEATER,CONFERENCE,EXHIBITION," +
                "FESTIVAL,WORKSHOP,MOVIE_PREMIERE,CHARITY,ESPORTS," +
                "LECTURE,MEETUP,OPEN_AIR,CARNIVAL,BUSINESS_FORUM): ");
        String eventTypeInput = scanner.nextLine().trim().toUpperCase();
        EventType eventType;
        try {
            eventType = EventType.valueOf(eventTypeInput);
        } catch (IllegalArgumentException exception) {
            System.out.print("Invalid event type. Defaulting to CONCERT: "
                    + exception.getMessage());
            log.error("Invalid event type. Defaulting to CONCERT: {}",
                    exception.getMessage());
            eventType = EventType.CONCERT;
        }

        System.out.print("Enter Event date (dd.MM.yyyy, e.g., 16.02.2025): ");
        String dateInput = scanner.nextLine().trim();
        LocalDate date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            date = LocalDate.parse(dateInput, formatter);
        } catch (DateTimeParseException exception) {
            System.out.print("Invalid date format. Defaulting to today’s date: " +
                    exception.getMessage());
            log.error("Invalid date format. Defaulting to today’s date: {}",
                    exception.getMessage());
            date = LocalDate.now();
        }

        System.out.print("Enter Event location: ");
        String location = scanner.nextLine().trim();

        System.out.print("Enter total ticket count: ");
        int totalTicketCount = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter sold ticket count: ");
        int soldTicketCount = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter ticket price (enter a number with a comma): ");
        double ticketPrice = scanner.nextDouble();
        scanner.nextLine();

        HashSet<String> artistList = new HashSet<>();
        System.out.print("Enter Artists (one per line, type '-' to finish): ");
        while (true) {
            String artist = scanner.nextLine().trim();
            if (artist.equalsIgnoreCase("-")) {
                break;
            }
            if (!artist.isEmpty()) {
                artistList.add(artist);
            }
        }

        Event event;
        if (artistList.isEmpty()) {
            if (soldTicketCount <= 0) {
                event = new Event(name, eventType, date, location,
                        totalTicketCount, ticketPrice);
            } else {
                event = new Event(name, eventType, date, location,
                        totalTicketCount, soldTicketCount, ticketPrice);
            }
        } else {
            event = new Event(name, eventType, date, location,
                    totalTicketCount, soldTicketCount, ticketPrice, artistList);
        }

        event.printEventInfo();
        return event;
    }

    private static void addEvent() {
        Event event = buildEvent();
        try {
            eventManager.addEvent(event);
        } catch (EventAlreadyInListException exception) {
            System.out.println("Event is already in the list " + exception.getMessage());
            log.error("Event is already in the list {}", exception.getMessage());
        }
    }

    private static void deleteEvent() {
        if (!eventManager.getEvents().isEmpty()) {
            String eventId = inputEventId();
            try {
                eventManager.removeEventById(eventId);
            } catch (EventIsNotInListException exception) {
                System.out.println("Event with id " + eventId + " is not in the list "
                        + exception.getMessage());
                log.error("Event with id {} is not in the list {}", eventId, exception.getMessage());
            }
        } else {
            System.out.println("No events in the list.");
            log.info("No events in the list.");
        }
    }

    private static String inputEventId() {
        System.out.print("Enter the ID of the event: ");
        return scanner.nextLine();
    }

    private static Event findEventById(String eventId) {
        try {
            return eventManager.getEventById(eventId);
        } catch (EventIsNotInListException exception) {
            System.out.println("Event not found: " + exception.getMessage());
            log.error("Event not found: {}", exception.getMessage());
            return null;
        }
    }

    private static void printArtistsList(Event event) {
        if (event.getArtistList().isEmpty()) {
            System.out.println("The artists list is empty.");
        } else {
            System.out.println("List of all artists for event ( " + event.getName() + ":");
            int count = 1;
            for (String artist : event.getArtistList()) {
                System.out.println("--------------------------------");
                System.out.println("Artist " + count + " : " + artist);
            }
        }
    }

    private static void calculateEventProfit(Event event) {
        System.out.print("Enter the expenses of the event: ");
        double expenses = scanner.nextDouble();
        scanner.nextLine();
        System.out.println(event.calculateProfit(expenses));
    }
}