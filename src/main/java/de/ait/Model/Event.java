package de.ait.Model;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Класс Event управляет списком артистов, используя HashSet для уникальности имен.
 */
public class Event {
    private String name; // Название мероприятия
    private Set<String> artistList; // Уникальные имена артистов

    /**
     * Конструктор мероприятия.
     * @param name Название события.
     */
    public Event(String name) {
        this.name = name;
        this.artistList = new HashSet<>(); // HashSet гарантирует уникальность имен
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

    // Геттеры
    public String getName() { return name; }
    public Set<String> getArtistList() { return new HashSet<>(artistList); } // Защита от изменений
}
