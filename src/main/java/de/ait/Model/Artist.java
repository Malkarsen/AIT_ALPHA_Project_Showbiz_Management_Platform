package project.Model;

import java.time.LocalDate;
import java.util.Objects;

public class Artist {
    private String name;
    private String genre;
    private LocalDate debutDate;

    public Artist(String name, String genre, LocalDate debutDate) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя артиста не может быть пустым.");
        }
        this.name = name;
        this.genre = genre;
        this.debutDate = debutDate;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public LocalDate getDebutDate() {
        return debutDate;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDebutDate(LocalDate debutDate) {
        this.debutDate = debutDate;
    }

    @Override
    public String toString() {
        return "Артист: " + name + ", Жанр: " + genre + ", Дата дебюта: " + debutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(name, artist.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
