package de.ait.App;

import de.ait.Core.EventManager;
import de.ait.Exceptions.EventAlreadyInListException;
import de.ait.Exceptions.EventIsNotInListException;
import de.ait.Model.Event;
import de.ait.Utilities.EventType;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class EventManagerApp {
    public static void main(String[] args) {
        EventManager eventManager = new EventManager();
        Event event1 = new Event("Rock Festival 2025", EventType.CONCERT,
                LocalDate.of(2025, 7, 15), "Berlin Olympic Stadium",
                50000, 32000, 75.50, new HashSet<>(Set.of("Flower")));
        Event event2 = new Event("Pop Festival 2025", EventType.FESTIVAL,
                LocalDate.of(2025, 10, 20), "Moscow Theatre",
                100000, 24000, 50.00);
        String eventId1 = null;
        String eventId2 = null;

        try {
            // Добавление двух разных событий
            eventId1 = eventManager.addEvent(event1);
            eventId2 = eventManager.addEvent(event2);
        } catch (EventAlreadyInListException exception) {
            System.out.println("Событие уже в списке " +  exception.getMessage());
            log.error("Событие уже в списке {}", exception.getMessage());
        }
        // Отображение всех элементов листа событий
        eventManager.displayAllEvents();
        // Получение списка событий и работа с ним
        System.out.println("Размер списка мероприятий: " + eventManager.getEvents().size());

        try {
            // Получение данных о событии (прибыль)
            Event otherEvent = eventManager.getEventById(eventId2);
            System.out.println("Событие " + otherEvent.getName() + " имеет прибыль в размере "
                    + otherEvent.calculateProfit(1_000_000));

            // Удаление событий разными способами (используя объект класса или по id)
            eventManager.removeEvent(event1);
            eventManager.removeEventById(eventId2);
        } catch (EventIsNotInListException exception) {
            System.out.println("События нет в списке " +  exception.getMessage());
            log.error("События нет в списке {}", exception.getMessage());
        }
    }
}
