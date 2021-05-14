package ru.geekbrains.lesson1;

public class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }

    public void ride(Transport transport) {
        System.out.printf("%s начал передвигаться на транспорте типа %s %n", this.name, transport.name);
    }

    public void stop() {
        System.out.printf("%s перестал передвигаться на всех видах транспорта%n", this.name);
    }
}
