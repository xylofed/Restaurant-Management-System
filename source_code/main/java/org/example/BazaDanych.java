package org.example;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BazaDanych {

    public static Connection connectDb() {
        Connection connection = null;
        try {
            // Załadowanie sterownika MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Połączenie z serwerem MySQL (bez wskazywania konkretnej bazy danych)
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
            Statement statement = connection.createStatement();

            // Sprawdzanie, czy baza danych istnieje
            ResultSet resultSet = statement.executeQuery(
                    "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'restauracja'");
            if (resultSet.next()) {
                // Jeśli baza danych istnieje, połącz się z nią
                connection.close();
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restauracja", "root", "");
            } else {
                // Jeśli baza danych nie istnieje, utwórz ją i zaimportuj dane
                statement.executeUpdate("CREATE DATABASE zarzadzanie_flota_pojazdow");
                connection.close();
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restauracja", "root", "");
                System.out.println("Baza danych została utworzona.");

                // Importowanie bazy danych z pliku SQL
                importDatabase(connection);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void importDatabase(Connection connection) {
        try {
            // Odczyt pliku SQL z zasobów
            InputStream inputStream = BazaDanych.class.getResourceAsStream("/restauracja.sql");
            if (inputStream == null) {
                System.out.println("Plik SQL nie został znaleziony!");
                return;
            }
            Scanner scanner = new Scanner(inputStream);
            StringBuilder sql = new StringBuilder();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith("--") && !line.trim().isEmpty()) {
                    sql.append(line);
                }
                if (line.endsWith(";")) {
                    try (Statement statement = connection.createStatement()) {
                        System.out.println("Executing SQL: " + sql); // Logowanie zapytań SQL
                        statement.execute(sql.toString());
                        sql.setLength(0);
                    } catch (SQLException e) {
                        System.err.println("Error executing SQL: " + sql); // Logowanie błędów SQL
                        e.printStackTrace();
                    }
                }
            }
            scanner.close();
            System.out.println("Import bazy danych zakończony.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}