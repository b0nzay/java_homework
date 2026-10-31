package org.example;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class SingerService {

    public List<String> findByGenreSortedByYear(Stream<Singer> singers, String genre) {
        return singers
                .filter(s -> s.getGenre().equalsIgnoreCase(genre))
                .sorted(Comparator.comparingInt(Singer::getDebutYear))
                .map(s -> s.getDebutYear() + " — " + s.getName().toUpperCase())
                .toList();
    }
}
