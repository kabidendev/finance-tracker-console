package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Account;
import com.company.repositories.interfaces.IAccountRepository;

import java.sql.*;

public class AccountRepository implements IAccountRepository {

    private final IDB db;

    public AccountRepository(IDB db) {
        this.db = db;
    }

    @Override
    public Account getById(int id) {
        String sql = "SELECT id, user_id, name, balance FROM accounts WHERE id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getDouble("balance")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Account getById error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateBalance(int accountId, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setDouble(1, newBalance);
            st.setInt(2, accountId);

            return st.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Account updateBalance error: " + e.getMessage());
            return false;
        }
    }

    // ---- если твой IRepository требует другие методы, просто оставь заглушки ----
    @Override
    public boolean create(Account entity) { return false; }
    public boolean update(Account entity) { return false; }
    public boolean delete(int id) { return false; }
}

