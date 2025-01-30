package project.Core;

import project.Model.Artist;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class ArtistManager {
    private HashSet<Artist> artistList;

    public ArtistManager() {
        this.artistList = new HashSet<>();
    }

    public void addArtist(Artist artist) {
        if (artist == null) {
            throw new IllegalArgumentException("Артист не может быть null.");
        }
        if (!artistList.add(artist)) {
            throw new IllegalArgumentException("Артист уже добавлен в список.");
        }
        System.out.println("Артист " + artist.getName() + " успешно добавлен.");
    }

    public void removeArtist(String artistName) {
        Artist toRemove = null;
        for (Artist artist : artistList) {
            if (artist.getName().equalsIgnoreCase(artistName)) {
                toRemove = artist;
                break;
            }
        }
        if (toRemove == null) {
            throw new NoSuchElementException("Артист не найден в списке.");
        }
        artistList.remove(toRemove);
        System.out.println("Артист " + artistName + " удален.");
    }

    public void listArtists() {
        if (artistList.isEmpty()) {
            System.out.println("Список артистов пуст.");
        } else {
            System.out.println("Список артистов:");
            for (Artist artist : artistList) {
                System.out.println("- " + artist);
            }
        }
    }

    public static void main(String[] args) {
        ArtistManager manager = new ArtistManager();

        Artist beatles = new Artist("The Beatles", "Rock", LocalDate.of(1960, 1, 1));
        Artist coldplay = new Artist("Coldplay", "Pop", LocalDate.of(1996, 6, 1));

        manager.addArtist(beatles);
        manager.addArtist(coldplay);
        manager.listArtists();

        manager.removeArtist("Coldplay");
        manager.listArtists();
    }
}
