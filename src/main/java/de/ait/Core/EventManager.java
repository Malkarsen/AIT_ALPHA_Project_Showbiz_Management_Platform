package de.ait.Core;

import de.ait.Model.Event;

import java.util.HashMap;

public class EventManager {
    private HashMap<String, Event> events; // список событий (String - id, Event - событие)

    public EventManager() {
        events = new HashMap<>();
    }

    public HashMap<String, Event> getEvents() {
        return new HashMap<>(events);
    }

    public void addEvent(Event event) {
        if (event == null) {
            // обработка исключения
            // логирование
            System.out.println("Error! Event is null");
        } else if ((event.getId() == null || event.getId().isEmpty())
                || (event.getName() == null || event.getName().isEmpty())
                || (event.getEventType() == null || event.getEventType().isEmpty())
                || (event.getDate() == null
                || event.getLocation() == null || event.getLocation().isEmpty())
                || (event.getTicketCount() <= 0))  {
            // обработка исключения
            // логирование
            System.out.println("Error! Event is invalid");
        } else if (events.containsKey(event.getId())){
            // обработка исключения
            // логирование
            System.out.println("Error! Event is already exists");
        } else {
            events.put(event.getId(), event);
        }
    }

    public void removeEvent(Event event) {
        if (event == null) {
            // обработка исключения
            // логирование
            System.out.println("Error! Event is null");
        } else if ((event.getId() == null || event.getId().isEmpty())
                || (event.getName() == null || event.getName().isEmpty())
                || (event.getEventType() == null || event.getEventType().isEmpty())
                || (event.getDate() == null
                || event.getLocation() == null || event.getLocation().isEmpty())
                || (event.getTicketCount() <= 0))  {
            // обработка исключения
            // логирование
            System.out.println("Error! Event is invalid");
        } else if (!events.containsKey(event.getId())){
            // обработка исключения
            // логирование
            System.out.println("Error! Event is not in the list");
        } else {
            events.remove(event.getId());
        }
    }
}
