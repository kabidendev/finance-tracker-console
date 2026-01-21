package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Account;
import com.company.repositories.interfaces.IAccountRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public boolean create(Account entity) {
        String sql = "INSERT INTO accounts(user_id, name, balance) VALUES (?, ?, ?) RETURNING id";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, entity.getUserId());
            st.setString(2, entity.getName());
            st.setDouble(3, entity.getBalance());

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                    return true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Account create error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Account> getByUserId(int userId) {
        String sql = "SELECT id, user_id, name, balance FROM accounts WHERE user_id = ? ORDER BY id";
        List<Account> list = new ArrayList<>();

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, userId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(new Account(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getDouble("balance")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Account getByUserId error: " + e.getMessage());
        }

        return list;
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
    public boolean update(Account entity) { return false; }
    public boolean delete(int id) { return false; }
}
