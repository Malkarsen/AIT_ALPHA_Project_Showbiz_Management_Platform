package de.ait.model;

import de.ait.utilities.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Класс Event управляет списком артистов и продажей билетов.
 * Содержит такую информацию о событии как: название, тип события,
 * дату, место проведения, общее и проданное количество билетов,
 * цену за один билет на данное мероприятие и список артистов
 */
@Slf4j
@Getter
@Setter
public class Event {
    private String id; // уникальный идентификатор
    private String name; // название
    private EventType eventType; // тип события
    private LocalDate date; // дата
    private String location; // место проведения
    private int totalTicketCount; // количество билетов
    private int soldTicketCount; // количество проданных билетов
    private double ticketPrice; // цена одного билета
    HashSet<String> artistList; // список артистов (String - имя артиста)

    /**
     * Создается новый объект события, если ещё не продано ни одного билета и не известны артисты
     * Уникальный идентификатор задается с помощью UUID (Universally Unique Identifier)
     * @param name              Название
     * @param eventType         Тип события
     * @param date              Дата
     * @param location          Место проведения
     * @param totalTicketCount  Количество билетов
     * @param ticketPrice       Цена одного билета
     */
    public Event(String name,
                 EventType eventType,
                 LocalDate date,
                 String location,
                 int totalTicketCount,
                 double ticketPrice) {
        this.id = UUID.randomUUID().toString(); // Генерация уникального идентификатора
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
     * Создается новый объект события, когда известно количество проданных билетов и не известен список артистов
     * Уникальный идентификатор задается с помощью UUID (Universally Unique Identifier)
     * @param name              Название
     * @param eventType         Тип события
     * @param date              Дата
     * @param location          Место проведения
     * @param totalTicketCount  Количество билетов
     * @param soldTicketCount   Количество проданных билетов
     * @param ticketPrice       Цена одного билета
     */
    public Event(String name,
                 EventType eventType,
                 LocalDate date,
                 String location,
                 int totalTicketCount,
                 int soldTicketCount,
                 double ticketPrice) {
        this.id = UUID.randomUUID().toString(); // Генерация уникального идентификатора
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
     * Создается новый объект события, когда все данные известны
     * Уникальный идентификатор задается с помощью UUID (Universally Unique Identifier)
     * @param name              Название
     * @param eventType         Тип события
     * @param date              Дата
     * @param location          Место проведения
     * @param totalTicketCount  Количество билетов
     * @param soldTicketCount   Количество проданных билетов
     * @param ticketPrice       Цена одного билета
     * @param artistList        Список артистов
     */
    public Event(String name,
                 EventType eventType,
                 LocalDate date,
                 String location,
                 int totalTicketCount,
                 int soldTicketCount,
                 double ticketPrice,
                 HashSet<String> artistList) {
        this.id = UUID.randomUUID().toString(); // Генерация уникального идентификатора
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
     * Возврат копии списка артистов
     * @return Копия списка артистов
     */
    public HashSet<String> getArtistList() {
        return new HashSet<>(artistList);
    }

    /**
     * Метод продажи билетов.
     * @param count Количество билетов, которое нужно продать.
     */
    public void sellTicket(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Количество билетов должно быть больше 0.");
        }
        if (soldTicketCount + count > totalTicketCount) {
            throw new IllegalArgumentException("Недостаточно билетов. Доступно только " + (totalTicketCount - soldTicketCount) + ".");
        }
        soldTicketCount += count;
        System.out.println(count + " билетов продано. Осталось билетов: " + (totalTicketCount - soldTicketCount));
    }

    /**
     * Метод отображения информации о билетах.
     */
    public void printTicketInfo() {
        System.out.println("Мероприятие: " + name);
        System.out.println("Всего билетов: " + totalTicketCount);
        System.out.println("Продано билетов: " + soldTicketCount);
        System.out.println("Осталось билетов: " + (totalTicketCount - soldTicketCount));
    }

    /**
     * Метод добавления артиста в мероприятие.
     * @param artistName Имя артиста.
     */
    public void addArtist(String artistName) {
        if (artistName == null || artistName.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя артиста не может быть пустым.");
        }
        if (!artistList.add(artistName)) {
            throw new IllegalArgumentException("Артист уже добавлен в мероприятие.");
        }
        System.out.println("Артист " + artistName + " добавлен в мероприятие: " + name);
    }

    /**
     * Метод удаления артиста из мероприятия.
     * @param artistName Имя артиста.
     */
    public void removeArtist(String artistName) {
        if (!artistList.contains(artistName)) {
            throw new NoSuchElementException("Артист не найден в мероприятии.");
        }
        artistList.remove(artistName);
        System.out.println("Артист " + artistName + " удален из мероприятия: " + name);
    }

    /**
     * Метод расчёта прибыли на основе цены билета и затрат
     * @param expenses   Расходы
     * @return           Чистая прибыль (значение меньше нуля - убыток)
     */
    public double calculateProfit(double expenses) {
        double totalIncome = soldTicketCount * ticketPrice; // Общий доход от продажи билетов
        double profit = totalIncome - expenses;
        if (profit > 0) {
            System.out.println("Событие " + name + " вышло на прибыль в размере: " + profit);
            log.info("Событие {} вышло на прибыль в размере: {}", name, profit);
        } else if (profit == 0) {
            System.out.println("Событие " + name + " вышло в ноль.");
            log.info("Событие {} вышло в ноль.", name);
        } else {
            System.out.println("Событие " + name + " вышло на убыток в размере: " + Math.abs(profit));
            log.info("Событие {} вышло на убыток в размере: {}", name, Math.abs(profit));
        }
        return profit;
    }

    /**
     * Метод отображения всей информации о событии.
     */
    public void printEventInfo() {
        System.out.println("Идентификатор: " + id);
        System.out.println("Название: " + name);
        System.out.println("Тип события: " + eventType);
        System.out.println("Дата: " + date);
        System.out.println("Место проведения: " + location);
        System.out.println("Количество билетов: " + totalTicketCount);
        System.out.println("Продано билетов: " + soldTicketCount);
        System.out.println("Осталось билетов: " + (totalTicketCount - soldTicketCount));
        if (artistList.isEmpty()) {
            System.out.println("Список артистов: пуст");
        } else {
            System.out.println("Список артистов: " + artistList);
        }
    }
}
