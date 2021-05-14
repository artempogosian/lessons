package ru.geekbrains.lesson1;

public class MainApp {
    public static void main(String[] args) {
        Person person = new Person("Гражданин");

        Transport skateboard = new Skateboard("Скейтборд");
        Transport bike = new Bike("Мотоцикл");
        Transport car = new Car("Машина");

        person.ride(skateboard);
        person.ride(bike);
        person.ride(car);

        person.stop();
    }
}
