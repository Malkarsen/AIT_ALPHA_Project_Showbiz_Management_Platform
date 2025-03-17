package de.ait.service;

import de.ait.exceptions.EventAlreadyInListException;
import de.ait.exceptions.EventIsNotInListException;
import de.ait.model.Event;
import de.ait.repository.EventManagerRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * The EventManager class manages a list of events.
 * It contains a list of events in the form of a HashMap, where the key is the id,
 * and the value is an object of the Event class.
 * It allows adding and removing events, searching for them by identifier,
 * and displaying a list of all events.
 */
@Slf4j
public class EventManagerImpl implements EventManagerRepository {
    private final HashMap<String, Event> events; // list of events (String - id, Event - event)

    public EventManagerImpl() {
        events = new HashMap<>();
    }

    /**
     * Returns a copy of the event list.
     * @return Copy of the event list
     */
    @Override
    public HashMap<String, Event> getEvents() {
        return new HashMap<>(events);
    }

    /**
     * Returns an event by its unique identifier,
     * if not found, displays an appropriate error message.
     * @param eventId Identifier of the event to be removed
     * @return Event by its unique identifier
     * @throws EventIsNotInListException If the event with this identifier is not found in the list
     */
    @Override
    public Event getEventById(String eventId) throws EventIsNotInListException {
        if (!events.containsKey(eventId)){
            log.error("Error! This event is not in the list");
            throw new EventIsNotInListException("Error! This event is not in the list");
        } else {
            return events.get(eventId);
        }
    }

    /**
     * Adds a new event to the list.
     * @param event Event to be added
     * @throws EventAlreadyInListException If an event with this identifier already exists in the list
     */
    @Override
    public String addEvent(Event event) throws EventAlreadyInListException {
        if (event == null) {
            log.error("Error! Event is null");
            throw new IllegalArgumentException("Error! Event is null");
        } else if (event.getName() == null || event.getName().isEmpty()) {
            log.error("Invalid name: Name cannot be empty or null");
            throw new IllegalArgumentException(
                    "Invalid name: Name cannot be empty or null");
        } else if (event.getEventType() == null) {
            log.error("Invalid event type: Event type cannot be empty or null");
            throw new IllegalArgumentException(
                    "Invalid event type: Event type cannot be empty or null");
        } else if (event.getDate() == null) {
            log.error("Invalid date: Date cannot be empty");
            throw new IllegalArgumentException(
                    "Invalid date: Date cannot be empty");
        } else if (event.getLocation() == null || event.getLocation().isEmpty()) {
            log.error("Invalid location: Location cannot be empty or null");
            throw new IllegalArgumentException(
                    "Invalid location: Location cannot be empty or null");
        } else if (event.getTotalTicketCount() <= 0) {
            log.error("Invalid ticket count: Ticket count cannot be less than or equal to zero");
            throw new IllegalArgumentException(
                    "Invalid ticket count: Ticket count cannot be less than or equal to zero");
        } else if (event.getSoldTicketCount() < 0) {
            log.error("Invalid sold ticket count: Ticket count cannot be less than or equal to zero");
            throw new IllegalArgumentException(
                    "Invalid sold ticket count: Ticket count cannot be less than or equal to zero");
        } else if (event.getTotalTicketCount() < event.getSoldTicketCount()) {
            log.error("Invalid sold ticket count: Sold ticket count cannot exceed the total number");
            throw new IllegalArgumentException(
                    "Invalid sold ticket count: Sold ticket count cannot exceed the total number");
        } else if (event.getTicketPrice() < 0) {
            log.error("Invalid ticket price: Ticket price cannot be less than zero");
            throw new IllegalArgumentException(
                    "Invalid ticket price: Ticket price cannot be less than zero");
        } else if (events.containsKey(event.getId())){
            log.error("Error! This event already exists in the list");
            throw new EventAlreadyInListException("Error! This event already exists in the list");
        } else {
            events.put(event.getId(), event);
            log.info("Event {} with Id {} added to the list", event.getName(), event.getId());
            System.out.println("Event " + event.getName() + " with Id " + event.getId() + " added to the list ");
            return event.getId();
        }
    }

    /**
     * Removes an event from the list.
     * @param event Event to be removed
     * @throws EventIsNotInListException If the event with this identifier is not found in the list
     */
    @Override
    public void removeEvent(Event event) throws EventIsNotInListException {
        if (event == null) {
            log.error("Error! Event is null");
            throw new IllegalArgumentException("Error! Event is null");
        } else if (event.getName() == null || event.getName().isEmpty()) {
            log.error("Invalid name: Name cannot be empty or null");
            throw new IllegalArgumentException(
                    "Invalid name: Name cannot be empty or null");
        } else if (event.getEventType() == null) {
            log.error("Invalid event type: Event type cannot be empty or null");
            throw new IllegalArgumentException(
                    "Invalid event type: Event type cannot be empty or null");
        } else if (event.getDate() == null) {
            log.error("Invalid date: Date cannot be empty");
            throw new IllegalArgumentException(
                    "Invalid date: Date cannot be empty");
        } else if (event.getLocation() == null || event.getLocation().isEmpty()) {
            log.error("Invalid location: Location cannot be empty or null");
            throw new IllegalArgumentException(
                    "Invalid location: Location cannot be empty or null");
        } else if (event.getTotalTicketCount() <= 0) {
            log.error("Invalid ticket count: Ticket count cannot be less than or equal to zero");
            throw new IllegalArgumentException(
                    "Invalid ticket count: Ticket count cannot be less than or equal to zero");
        } else if (event.getSoldTicketCount() < 0) {
            log.error("Invalid sold ticket count: Ticket count cannot be less than or equal to zero");
            throw new IllegalArgumentException(
                    "Invalid sold ticket count: Ticket count cannot be less than or equal to zero");
        } else if (event.getTotalTicketCount() < event.getSoldTicketCount()) {
            log.error("Invalid sold ticket count: Sold ticket count cannot exceed the total number");
            throw new IllegalArgumentException(
                    "Invalid sold ticket count: Sold ticket count cannot exceed the total number");
        } else if (event.getTicketPrice() < 0) {
            log.error("Invalid ticket price: Ticket price cannot be less than zero");
            throw new IllegalArgumentException(
                    "Invalid ticket price: Ticket price cannot be less than zero");
        } else if (!events.containsKey(event.getId())){
            log.error("Error! This event is not in the list");
            throw new EventIsNotInListException("Error! This event is not in the list");
        } else {
            events.remove(event.getId());
            log.info("Event {} with Id {} removed from the list", event.getName(), event.getId());
            System.out.println("Event " + event.getName() + " with Id " + event.getId() + " removed from the list ");
        }
    }

    /**
     * Removes an event from the list by its identifier.
     * @param eventId Identifier of the event to be removed
     * @throws EventIsNotInListException If the event with this identifier is not found in the list
     */
    @Override
    public void removeEventById(String eventId) throws EventIsNotInListException {
        if (!events.containsKey(eventId)){
            log.error("Error! This event is not in the list");
            throw new EventIsNotInListException("Error! This event is not in the list");
        } else {
            events.remove(eventId);
            log.info("Event with Id {} removed from the list", eventId);
            System.out.println("Event with Id " + eventId + " removed from the list ");
        }
    }

    /**
     * Displays a list of all contracts.
     * If the list is empty, an appropriate message is displayed.
     */
    @Override
    public void displayAllEvents() {
        if (events.isEmpty()) {
            System.out.println("The event list is empty.");
        } else {
            System.out.println("List of all events:");
            int count = 1;
            for (Event event : events.values()) {
                System.out.println("--------------------------------");
                System.out.println("Event "+ count);
                event.printEventInfo();
                count++;
            }
        }
    }
}