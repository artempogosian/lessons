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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainApp {
    private static final SessionFactory factory = buildSessionFactory();

    public static void main(String[] args) {
        Session session = null;
        try {
            session = factory.openSession();

            Repository<Student> studentsRepository = new Repository<>(session);

            Student student1 = new Student("Яшин", "Федор", "Вадимович");
            Student student2 = new Student("Круглов", "Вадим", "Николаевич");
            Student student3 = new Student("Сухорукова", "Регина", "Петровна");

            studentsRepository.createOrUpdate(student1);
            studentsRepository.createOrUpdate(student2);
            studentsRepository.createOrUpdate(student3);

            Student s = studentsRepository.findOne(Student.class, student3.id);
            System.out.println(s.getMiddleName());
            s.setMiddleName("Васильевна");
            studentsRepository.createOrUpdate(s);
            System.out.println(s.getMiddleName());
            studentsRepository.deleteById(Student.class, student3.id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            if (factory != null && !factory.isClosed()) {
                factory.close();
            }
        }
    }

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
