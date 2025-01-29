package de.ait.Model;

import java.time.LocalDate;
import java.util.HashSet;

public class Event {
    private String id; // уникальный идентификатор
    private String name; // название
    private String eventType; // тип события
    private LocalDate date; // дата
    private String location; // место проведения
    private int ticketCount; // количество билетов
    HashSet<String> artistList; // список артистов (String - имя артиста)

    public Event(String id,
                 String name,
                 String eventType,
                 LocalDate date,
                 String location,
                 int ticketCount) {
        this.id = id;
        this.name = name;
        this.eventType = eventType;
        this.date = date;
        this.location = location;
        this.ticketCount = ticketCount;
        this.artistList = new HashSet<>();
    }

    public Event(String id,
                 String name,
                 String eventType,
                 LocalDate date,
                 String location,
                 int ticketCount,
                 HashSet<String> artistList) {
        this(id, name, eventType, date, location, ticketCount);
        this.artistList = artistList;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEventType() {
        return eventType;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public HashSet<String> getArtistList() {
        return new HashSet<>(artistList);
    }

    // метод учета билетов
    public void sellTicket(int count) {
        // TODO Artur Begichev
    }

    // добавление нового артиста
    public void addArtist(String artistName) {
        // TODO Artur Begichev
    }

    // удаление артиста
    public void removeArtist(String artistName) {
        // TODO Artur Begichev
    }

    // метод расчета прибыли и затрат
    double calculateProfit(double income, double expenses) {
        // double income; // доход
        // double expenses; // затраты

        // основная логика расчета прибыли, затрат и вывода результата
        // TODO
        return income - expenses;
    }
}
