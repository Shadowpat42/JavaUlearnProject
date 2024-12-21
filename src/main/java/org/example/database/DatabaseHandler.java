package org.example.database;

import org.example.model.Country;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/country.db";

    public static void createDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                String createTableSQL = """
                        CREATE TABLE IF NOT EXISTS countries (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            name TEXT NOT NULL,
                            subregion TEXT NOT NULL,
                            region TEXT NOT NULL,
                            internetUsers INTEGER NOT NULL,
                            population INTEGER NOT NULL
                        );
                        """;
                Statement stmt = conn.createStatement();
                stmt.execute(createTableSQL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertData(List<Country> countries) {
        String insertSQL = "INSERT INTO countries (name, subregion, region, internetUsers, population) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            for (Country country : countries) {
                pstmt.setString(1, country.getName());
                pstmt.setString(2, country.getSubregion());
                pstmt.setString(3, country.getRegion());
                pstmt.setLong(4, country.getInternetUsers());
                pstmt.setLong(5, country.getPopulation());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
