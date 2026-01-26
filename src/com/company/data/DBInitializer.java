package com.company.data;

import com.company.data.interfaces.IDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            "id SERIAL PRIMARY KEY, " +
            "email VARCHAR(255) NOT NULL UNIQUE, " +
            "password VARCHAR(255) NOT NULL, " +
            "role VARCHAR(20) NOT NULL DEFAULT 'USER'" +
            ")";

    private final IDB db;

    public DBInitializer(IDB db) {
        this.db = db;
    }

    public void initialize() {
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_USERS_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
