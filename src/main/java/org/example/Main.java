package org.example;

public class Main {
    public static void main(String[] args) {
        Animal a = new Cat(); a.sound();
    }
}

class Animal {
    void sound() {
        System.out.println("Animal");
    }
}
class Cat extends Animal {
    void sound() {
        System.out.println("Meow");
    }
}