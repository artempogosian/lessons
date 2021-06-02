package ru.geekbrains.lesson7;

@AppTable(title = "products")
public class Product {
    public Product(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public Product(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @AppColumn
    private int id;

    @AppColumn
    private String name;

    @AppColumn
    private int type;

    private String description;
}
