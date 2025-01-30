package de.ait.Model;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Класс Event управляет списком артистов и продажей билетов.
 */
public class Event {
    private String name; // Название мероприятия
    private Set<String> artistList; // Уникальные имена артистов
    private int totalTickets; // Всего билетов
    private int ticketsSold; // Количество проданных билетов

    /**
     * Конструктор мероприятия.
     * @param name Название события.
     * @param totalTickets Количество билетов.
     */
    public Event(String name, int totalTickets) {
        this.name = name;
        this.artistList = new HashSet<>(); // HashSet гарантирует уникальность имен
        this.totalTickets = totalTickets;
        this.ticketsSold = 0; // Изначально билеты не проданы
    }

    /**
     * Метод продажи билетов.
     * @param count Количество билетов, которое нужно продать.
     */
    public void sellTickets(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Количество билетов должно быть больше 0.");
        }
        if (ticketsSold + count > totalTickets) {
            throw new IllegalArgumentException("Недостаточно билетов. Доступно только " + (totalTickets - ticketsSold) + ".");
        }
        ticketsSold += count;
        System.out.println(count + " билетов продано. Осталось билетов: " + (totalTickets - ticketsSold));
    }

    /**
     * Метод отображения информации о билетах.
     */
    public void printTicketInfo() {
        System.out.println("Мероприятие: " + name);
        System.out.println("Всего билетов: " + totalTickets);
        System.out.println("Продано билетов: " + ticketsSold);
        System.out.println("Осталось билетов: " + (totalTickets - ticketsSold));
    }

    /**
     * Метод добавления артиста в мероприятие.
     * @param artist Имя артиста.
     */
    public void addArtist(String artist) {
        if (artist == null || artist.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя артиста не может быть пустым.");
        }
        if (!artistList.add(artist)) {
            throw new IllegalArgumentException("Артист уже добавлен в мероприятие.");
        }
        System.out.println("Артист " + artist + " добавлен в мероприятие: " + name);
    }

    /**
     * Метод удаления артиста из мероприятия.
     * @param artist Имя артиста.
     */
    public void removeArtist(String artist) {
        if (!artistList.contains(artist)) {
            throw new NoSuchElementException("Артист не найден в мероприятии.");
        }
        artistList.remove(artist);
        System.out.println("Артист " + artist + " удален из мероприятия: " + name);
    }

    /**
     * Метод вывода списка артистов мероприятия.
     */
    public void listArtists() {
        if (artistList.isEmpty()) {
            System.out.println("В мероприятии " + name + " нет артистов.");
        } else {
            System.out.println("Список артистов в мероприятии " + name + ":");
            for (String artist : artistList) {
                System.out.println("- " + artist);
            }
        }
    }
}
