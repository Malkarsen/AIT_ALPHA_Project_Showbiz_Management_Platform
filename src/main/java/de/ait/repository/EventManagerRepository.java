package de.ait.repository;

import de.ait.exceptions.EventAlreadyInListException;
import de.ait.exceptions.EventIsNotInListException;
import de.ait.model.Event;

import java.util.HashMap;

public interface EventManagerRepository {
    HashMap<String, Event> getEvents();

    Event getEventById(String eventId) throws EventIsNotInListException;

    String addEvent(Event event) throws EventAlreadyInListException;

    void removeEvent(Event event) throws EventIsNotInListException;

    void removeEventById(String eventId) throws EventIsNotInListException;

    void displayAllEvents();
}
