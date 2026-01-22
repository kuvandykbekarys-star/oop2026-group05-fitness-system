package edu.aitu.oop3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DemoUsersExample {

    public static void main(String[] args) {
        System.out.println("Demo: work with users");

        try (Connection connection = DatabaseConnection.getConnection()) {

            createTableIfNeeded(connection);
            insertUser(connection, "Alice", "alice@example.com");
            insertUser(connection, "Bob", "bob@example.com");

            printAllUsers(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // создаёт таблицу если её нет
    private static void createTableIfNeeded(Connection connection) throws SQLException {
        String sql = """
                create table if not exists demo_users (
                    id serial primary key,
                    name varchar(100) not null,
                    email varchar(100) unique not null
                );
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
            System.out.println("Table ready");
        }
    }

    // добавляет пользователя
    private static void insertUser(Connection connection, String name, String email) throws SQLException {
        String sql = "insert into demo_users (name, email) values (?, ?) on conflict (email) do nothing";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
        }
    }

    // выводит всех пользователей
    private static void printAllUsers(Connection connection) throws SQLException {
        String sql = "select id, name, email from demo_users";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("email")
                );
            }
        }
    }
}
