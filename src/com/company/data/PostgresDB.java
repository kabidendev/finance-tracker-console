package com.company.data;

import com.company.data.interfaces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresDB implements IDB {
    private final String url;
    private final String user;
    private final String password;

    public PostgresDB(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
