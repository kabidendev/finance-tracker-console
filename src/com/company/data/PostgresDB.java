package com.company.data;

import com.company.data.interfaces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private static PostgresDB instance;
    private final String url;
    private final String username;
    private final String password;

    public PostgresDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    public static synchronized PostgresDB getInstance(String url, String username, String password) {
        if (instance == null) {
            instance = new PostgresDB(url, username, password);
        }
        return instance;
    }
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
