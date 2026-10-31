package org.example;

import java.time.Year;

public class Singer {
    private static final int MIN_DEBUT_YEAR = 1900;

    private final String name;
    private final String genre;
    private final int debutYear;
    private final int albums;

    public Singer(String name, String genre, int debutYear, int albums) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("Жанр не может быть пустым");
        }
        int currentYear = Year.now().getValue();
        if (debutYear < MIN_DEBUT_YEAR || debutYear > currentYear) {
            throw new IllegalArgumentException(
                    "Год дебюта должен быть от " + MIN_DEBUT_YEAR + " до " + currentYear);
        }
        if (albums < 0) {
            throw new IllegalArgumentException("Число альбомов не может быть отрицательным");
        }
        this.name = name.trim();
        this.genre = genre.trim();
        this.debutYear = debutYear;
        this.albums = albums;
    }

    public String getName() { return name; }
    public String getGenre() { return genre; }
    public int getDebutYear() { return debutYear; }
    public int getAlbums() { return albums; }

    @Override
    public String toString() {
        return String.format("%s | %s | %d | альбомов: %d", name, genre, debutYear, albums);
    }
}
