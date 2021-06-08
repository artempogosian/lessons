package ru.geekbrains.lesson7;

// Создайте аннотацию @AppTable и @AppColumn. AppTable может быть добавлена к классу, AppColumn к полю.
// Создайте и разметьте этими аннотациями класс, имеющий только поля типа int и String.
// Реализуйте класс, позволяющий по размеченному типу данных из п. 1, создавать таблицу, и сохранять объекты в базу данных.
// Может получиться что-то вроде такого кода в Main классе
//
// public static void main(String[] args) {
//     ...
//     CatDao catDao = new ...();
//     catDao.createTable();
//     catDao.save(new Cat(“Barsik”));
// }
//
// В качестве СУБД желательно использовать SQLite

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Optional;
import java.sql.SQLException;
import java.sql.Statement;

public class MainApp {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ProductDao productDao;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:javadb.db");
            statement = connection.createStatement();
            productDao = new ProductDao(connection, statement);

            productDao.createTable();
            productDao.clearTable();

            productDao.add(new Product("product1", 1));
            productDao.add(new Product("product2", 1));
            productDao.add(new Product("product3", 1));

            productDao.save();

            ArrayList<Product> products = productDao.getAll();

            Optional<Product> product1 = productDao.getById(1);
            Optional<Product> product2 = productDao.getById(2);
            Optional<Product> product3 = productDao.getById(3);

            System.out.println();

            productDao.dropTable();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
