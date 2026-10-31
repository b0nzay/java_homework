package org.example;

import java.util.stream.Stream;

public class SingerRepository {
    private final MyArrayList<Singer> singers = new MyArrayList<>();

    public void add(Singer newSinger) {
        singers.add(newSinger);
    }

    public void addAll(MyArrayList<Singer> newSingers) {
        singers.addAll(newSingers);
    }

    public Singer get(int index) {
        return singers.get(index);
    }

    public Singer remove(int index) {
        return singers.remove(index);
    }

    public int size() {
        return singers.size();
    }

    public boolean isEmpty() {
        return singers.isEmpty();
    }

    public Stream<Singer> stream() {
        return singers.stream();
    }
}
