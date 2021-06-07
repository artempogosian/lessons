package ru.geekbrains.lesson8;

/*
    Создать обобщенный класс Repository<T, ..>, позволяющий выполнять операции:
        а. сохранения объекта в БД;
        б. загрузки объекта из базы по id.
    Пример:
    Repository<Student, ...> studentsRepository = new Repository<>(...);
    Student s = studentsRepository.get(1L);

    Для решения задачи возможно понадобится применение Reflection API.

*/

import ru.geekbrains.lesson5.Car;
import ru.geekbrains.lesson5.Race;
import ru.geekbrains.lesson5.Road;
import ru.geekbrains.lesson5.Tunnel;

import java.util.concurrent.*;

public class MainApp {
    public static void main(String[] args) {
        Repository<Student> studentsRepository = new Repository<>();
//        Я́шин Федор Вадимович
//        Круглов Вадим Николаевич
//        Сухорукова Регина Петровна
//        Муравьёв Павел Анатольевич
//        Егоров Никифор Игнатьевич
        Student student1 = new Student("Яшин", "Федор", "Вадимович");
        Student student2 = new Student("Круглов", "Вадим", "Николаевич");
        Student student3 = new Student("Сухорукова", "Регина", "Петровна");
        Student student4 = new Student("Муравьёв", "Павел", "Анатольевич");
        Student student5 = new Student("Егоров", "Никифор", "Игнатьевич");
        long id = studentsRepository.add(student1);
        Student s = studentsRepository.get(id);
    }
}
