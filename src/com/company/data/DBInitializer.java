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
        createAccountsTable();
        createCategoriesTable();
        createTransactionsTable();
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

    private void createAccountsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS accounts (" +
                "id SERIAL PRIMARY KEY," +
                "user_id INT NOT NULL," +
                "name VARCHAR(100) NOT NULL," +
                "balance DOUBLE PRECISION NOT NULL DEFAULT 0," +
                "CONSTRAINT fk_accounts_user FOREIGN KEY (user_id) REFERENCES users(id)" +
                ");";

        try (Connection con = db.getConnection(); Statement st = con.createStatement()) {
            st.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createCategoriesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS categories (" +
                "id SERIAL PRIMARY KEY," +
                "user_id INT NULL," +
                "name VARCHAR(100) NOT NULL," +
                "type VARCHAR(10) NOT NULL CHECK (type IN ('INCOME', 'EXPENSE'))," +
                "CONSTRAINT fk_categories_user FOREIGN KEY (user_id) REFERENCES users(id)" +
                ");";
        try (Connection con = db.getConnection(); Statement st = con.createStatement()) {
            st.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createTransactionsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id SERIAL PRIMARY KEY," +
                "user_id INT NOT NULL," +
                "type VARCHAR(10) NOT NULL," +
                "amount DOUBLE PRECISION NOT NULL," +
                "category_id INT NULL," +
                "account_from_id INT NULL," +
                "account_to_id INT NULL," +
                "created_at TIMESTAMP NOT NULL DEFAULT now()," +
                "comment VARCHAR(255)," +
                "CONSTRAINT fk_transactions_user FOREIGN KEY (user_id) REFERENCES users(id)," +
                "CONSTRAINT fk_transactions_category FOREIGN KEY (category_id) REFERENCES categories(id)," +
                "CONSTRAINT fk_transactions_from FOREIGN KEY (account_from_id) REFERENCES accounts(id)," +
                "CONSTRAINT fk_transactions_to FOREIGN KEY (account_to_id) REFERENCES accounts(id)" +
                ");";
        try (Connection con = db.getConnection(); Statement st = con.createStatement()) {
            st.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
