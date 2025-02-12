package de.ait.Core;

import de.ait.Exceptions.EventAlreadyInListException;
import de.ait.Exceptions.EventIsNotInListException;
import de.ait.Model.Event;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * Класс EventManager управляет списком мероприятий.
 * Содержит список событий в виде HashMap, где ключ - это id,
 * а значение - объект класса Event
 * Позволяет добавлять и удалять события, искать их по идентификатору
 * и выводить список всех событий
 */
@Slf4j
public class EventManager {
    private HashMap<String, Event> events; // список событий (String - id, Event - событие)

    public EventManager() {
        events = new HashMap<>();
    }

    /**
     * Возврат копии списка событий
     * @return Копия списка событий
     */
    public HashMap<String, Event> getEvents() {
        return new HashMap<>(events);
    }

    /**
     * Возвращает событие по его уникальному идентификатору,
     * если не найдено выводит соответствующее сообщение об ошибке
     * @param eventId Идентификатор удаляемого события
     * @return Событие по его уникальному идентификатору
     * @throws EventIsNotInListException Если событие с таким идентификатором не найдено в списке
     */
    public Event getEventById(String eventId) throws EventIsNotInListException {
        if (eventId == null || eventId.isEmpty()) {
            log.error("Некорректный уникальный идентификатор: Id не может быть пустым или равным нулю");
            throw new IllegalArgumentException(
                    "Некорректный уникальный идентификатор: Id не может быть пустым или равным нулю");
        } else if (!events.containsKey(eventId)){
            log.error("Ошибка! Данного события нет в списке");
            throw new EventIsNotInListException("Ошибка! Данного события нет в списке");
        } else {
            return events.get(eventId);
        }
    }

    /**
     * Добавляет новое событие в список
     * @param event Событие для добавления
     * @throws EventAlreadyInListException Если событие с таким идентификатором уже есть в списке
     */
    public void addEvent(Event event) throws EventAlreadyInListException {
        if (event == null) {
            log.error("Ошибка! Событие равно нулю");
            throw new IllegalArgumentException("Ошибка! Событие равно нулю");
        } else if (event.getId() == null || event.getId().isEmpty()) {
            log.error("Некорректный уникальный идентификатор: Id не может быть пустым или равным нулю");
            throw new IllegalArgumentException(
                    "Некорректный уникальный идентификатор: Id не может быть пустым или равным нулю");
        } else if (event.getName() == null || event.getName().isEmpty()) {
            log.error("Некорректное имя: Имя не может быть пустым или равным нулю");
            throw new IllegalArgumentException(
                    "Некорректное имя: Имя не может быть пустым или равным нулю");
        } else if (event.getEventType() == null) {
            log.error("Некорректный тип события: Тип события не может быть пустым или равным нулю");
            throw new IllegalArgumentException(
                    "Некорректный тип события: Тип события не может быть пустым или равным нулю");
        } else if (event.getDate() == null) {
            log.error("Некорректная дата: Дата не может быть пустой");
            throw new IllegalArgumentException(
                    "Некорректная дата: Дата не может быть пустой");
        } else if (event.getLocation() == null || event.getLocation().isEmpty()) {
            log.error("Некорректное место проведения: Место проведения не может быть пустым или равным нулю");
            throw new IllegalArgumentException(
                    "Некорректное место проведения: Место проведения не может быть пустым или равным нулю");
        } else if (event.getTotalTicketCount() <= 0) {
            log.error("Некорректное количество билетов: Количество билетов не может быть меньше или равным нулю");
            throw new IllegalArgumentException(
                    "Некорректное количество билетов: Количество билетов не может быть меньше или равным нулю");
        } else if (event.getTicketPrice() <= 0) {
            log.error("Некорректная цена билета: Цена билета не может быть меньше или равной нулю");
            throw new IllegalArgumentException(
                    "Некорректная цена билета: Цена билета не может быть меньше или равной нулю");
        } else if (event.getArtistList() == null || event.getArtistList().isEmpty())  {
            log.error("Некорректный список исполнителей: Список исполнителей не может быть пустым или равным нулю");
            throw new IllegalArgumentException(
                    "Некорректный список исполнителей: Список исполнителей не может быть пустым или равным нулю");
        } else if (events.containsKey(event.getId())){
            log.error("Ошибка! Данное событие уже есть в списке");
            throw new EventAlreadyInListException("Ошибка! Данное событие уже есть в списке");
        } else {
            events.put(event.getId(), event);
            log.info("Событие {} с Id {} добавлено в список", event.getName(), event.getId());
            System.out.println("Событие " + event.getName() + " с Id " + event.getId() + " добавлено в список ");
        }
    }

    /**
     * Удаляет событие из списка
     * @param event Событие для удаления
     * @throws EventIsNotInListException Если событие с таким идентификатором не найдено в списке
     */
    public void removeEvent(Event event) throws EventIsNotInListException {
        if (event == null) {
            log.error("Ошибка! Событие равно нулю");
            throw new IllegalArgumentException("Ошибка! Событие равно нулю");
        } else if (event.getId() == null || event.getId().isEmpty()) {
            log.error("Некорректный уникальный идентификатор: Id не может быть пустым или равным нулю");
            throw new IllegalArgumentException(
                    "Некорректный уникальный идентификатор: Id не может быть пустым или равным нулю");
        } else if (event.getName() == null || event.getName().isEmpty()) {
            log.error("Некорректное имя: Имя не может быть пустым или равным нулю");
            throw new IllegalArgumentException(
                    "Некорректное имя: Имя не может быть пустым или равным нулю");
        } else if (event.getEventType() == null) {
            log.error("Некорректный тип события: Тип события не может быть пустым или равным нулю");
            throw new IllegalArgumentException(
                    "Некорректный тип события: Тип события не может быть пустым или равным нулю");
        } else if (event.getDate() == null) {
            log.error("Некорректная дата: Дата не может быть пустой");
            throw new IllegalArgumentException(
                    "Некорректная дата: Дата не может быть пустой");
        } else if (event.getLocation() == null || event.getLocation().isEmpty()) {
            log.error("Некорректное место проведения: Место проведения не может быть пустым или равным нулю");
            throw new IllegalArgumentException(
                    "Некорректное место проведения: Место проведения не может быть пустым или равным нулю");
        } else if (event.getTotalTicketCount() <= 0) {
            log.error("Некорректное количество билетов: Количество билетов не может быть меньше или равным нулю");
            throw new IllegalArgumentException(
                    "Некорректное количество билетов: Количество билетов не может быть меньше или равным нулю");
        } else if (event.getTicketPrice() <= 0) {
            log.error("Некорректная цена билета: Цена билета не может быть меньше или равной нулю");
            throw new IllegalArgumentException(
                    "Некорректная цена билета: Цена билета не может быть меньше или равной нулю");
        } else if (event.getArtistList() == null || event.getArtistList().isEmpty())  {
            log.error("Некорректный список исполнителей: Список исполнителей не может быть пустым или равным нулю");
            throw new IllegalArgumentException(
                    "Некорректный список исполнителей: Список исполнителей не может быть пустым или равным нулю");
        } else if (!events.containsKey(event.getId())){
            log.error("Ошибка! Данного события нет в списке");
            throw new EventIsNotInListException("Ошибка! Данного события нет в списке");
        } else {
            events.remove(event.getId());
            log.info("Событие {} с Id {} удалено из списка", event.getName(), event.getId());
            System.out.println("Событие " + event.getName() + " с Id " + event.getId() + " удалено из списка ");
        }
    }

    /**
     * Удаляет событие из списка по его идентификатору
     * @param eventId Идентификатор удаляемого события
     * @throws EventIsNotInListException Если событие с таким идентификатором не найдено в списке
     */
    public void removeEventById(String eventId) throws EventIsNotInListException {
        if (eventId == null || eventId.isEmpty()) {
            log.error("Некорректный уникальный идентификатор: Id не может быть пустым или равным нулю");
            throw new IllegalArgumentException(
                    "Некорректный уникальный идентификатор: Id не может быть пустым или равным нулю");
        } else if (!events.containsKey(eventId)){
            log.error("Ошибка! Данного события нет в списке");
            throw new EventIsNotInListException("Ошибка! Данного события нет в списке");
        } else {
            events.remove(eventId);
            log.info("Событие с Id {} удалено из списка", eventId);
            System.out.println("Событие с Id " + eventId + " удалено из списка ");
        }
    }

    /**
     * Отображает список всех контрактов.
     * Если список пуст, выводится соответствующее сообщение.
     */
    public void displayAllEvents() {
        if (events.isEmpty()) {
            System.out.println("Список событий пуст.");
        } else {
            System.out.println("Список всех событий:");
            int count = 1;
            for (Event event : events.values()) {
                System.out.println("--------------------------------");
                System.out.println("Событие "+ count);
                System.out.println(event);
            }
        }
    }
}
