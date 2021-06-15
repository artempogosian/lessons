package ru.geekbrains.lesson7;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductDao {
    private final Connection connection;
    private final Statement stmt;
    private String table;
    private final Map<String, String> fields = new HashMap<>();

    public ProductDao(Connection connection, Statement stmt) {
        this.connection = connection;
        this.stmt = stmt;
        initialize();
    }

    private void initialize() {
        Class<Product> cl = Product.class;
        if (!cl.isAnnotationPresent(AppTable.class)) {
            throw new IllegalArgumentException("Invalid class");
        }
        table = cl.getAnnotation(AppTable.class).title();
        Map<Class, String> converter = new HashMap<>();
        converter.put(int.class, "integer");
        converter.put(String.class, "text");
        Field[] fields = cl.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(AppColumn.class)) {
                String name = field.getName();
                String type = converter.get(field.getType());
                this.fields.put(name, type);
            }
        }
    }

    //region Methods
    public void add(Product product) throws SQLException, NoSuchFieldException, IllegalAccessException {
        Map<String, String> kvm = new HashMap<>();
        for (Map.Entry<String, String> f : fields.entrySet()) {
            if (f.getKey().equals("id")) continue;
            Field field = product.getClass().getDeclaredField(f.getKey());
            field.setAccessible(true);
            Object o = field.get(product);
            kvm.put(field.getName(), o.toString());
        }
        String columns = String.join(", ", kvm.keySet());
        String values = kvm.values().stream().map(x -> "'" + x + "'").collect(Collectors.joining(", "));
        String query = String.format("INSERT INTO " + table + " (%s) VALUES (%s);", columns, values);
        stmt.addBatch(query);
        System.out.println(query);
    }

    public void save() throws SQLException {
        stmt.executeBatch();
    }

    // Не хватило времени сделать через reflection
    public ArrayList<Product> getAll() throws SQLException {
        try (ResultSet rs = stmt.executeQuery("SELECT * FROM " + table + ";")) {
            ArrayList<Product> result = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("type")
                );
                result.add(product);
            }
            return result;
        }
    }

    // Не хватило времени сделать через reflection
    public Optional<Product> getById(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE id = ?;");
        ps.setInt(1, id);
        ArrayList<Product> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("type")
                );
                result.add(product);
            }
        }
        return result.stream().findFirst();
    }

    //endregion

    //region Table operations
    public void createTable() throws SQLException {
        Class<Product> cl = Product.class;
        if (!cl.isAnnotationPresent(AppTable.class)) {
            throw new IllegalArgumentException("Invalid class");
        }
        Map<Class, String> converter = new HashMap<>();
        converter.put(int.class, "integer");
        converter.put(String.class, "text");
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        builder.append(cl.getAnnotation(AppTable.class).title());
        builder.append(" (");
        Field[] fields = cl.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(AppColumn.class)) {
                builder
                        .append(field.getName())
                        .append(" ")
                        .append(converter.get(field.getType()));
                if (field.getName().equals("id")) {
                    builder.append(" PRIMARY KEY AUTOINCREMENT");
                }
                builder.append(", ");
            }
        }
        builder.setLength(builder.length() - 2);
        builder.append(");");
        String query = builder.toString();
        System.out.println(query);
        stmt.executeUpdate(query);
    }

    public void dropTable() throws SQLException {
        String query = "DROP TABLE IF EXISTS " + table + ";";
        stmt.executeUpdate(query);
        System.out.println(query);
    }

    public void clearTable() throws SQLException {
        String query = "DELETE FROM " + table + ";";
        stmt.executeUpdate(query);
        System.out.println(query);
    }
    //endregion
}
