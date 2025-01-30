package project;

import java.util.HashSet;
import java.util.NoSuchElementException;

public class ArtistManager {
    private HashSet<String> artistList;

    public ArtistManager() {
        this.artistList = new HashSet<>();
    }

    // Метод для добавления артиста
    public void addArtist(String artist) {
        if (artist == null || artist.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя артиста не может быть пустым.");
        }
        if (!artistList.add(artist)) {
            throw new IllegalArgumentException("Артист уже добавлен в список.");
        }
        System.out.println("Артист " + artist + " успешно добавлен.");
    }

    // Метод для удаления артиста
    public void removeArtist(String artist) {
        if (!artistList.contains(artist)) {
            throw new NoSuchElementException("Артист не найден в списке.");
        }
        artistList.remove(artist);
        System.out.println("Артист " + artist + " удален из списка.");
    }

    // Метод для вывода списка артистов
    public void listArtists() {
        if (artistList.isEmpty()) {
            System.out.println("Список артистов пуст.");
        } else {
            System.out.println("Список артистов:");
            for (String artist : artistList) {
                System.out.println("- " + artist);
            }
        }
    }

    public static void main(String[] args) {
        ArtistManager manager = new ArtistManager();

        // Тестирование методов
        manager.addArtist("Beatles");
        manager.addArtist("Coldplay");
        manager.listArtists();

        manager.removeArtist("Coldplay");
        manager.listArtists();
    }
}
