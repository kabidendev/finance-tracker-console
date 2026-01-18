package com.company.data;

import com.company.data.interfaces.IDB;

import java.sql.Connection;
import java.sql.Statement;

public class DBInitializer {
    private final IDB db;

    public DBInitializer(IDB db) {
        this.db = db;
    }

    public void init() {
        createUsersTable();
    }

    private void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) UNIQUE NOT NULL," +
                "password VARCHAR(100) NOT NULL," +
                "role VARCHAR(20) NOT NULL" +
                ")";
        try (Connection con = db.getConnection(); Statement st = con.createStatement()) {
            st.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
