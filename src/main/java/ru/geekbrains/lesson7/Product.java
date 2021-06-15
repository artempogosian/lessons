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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
