package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.exceptions.NotFoundException;
import com.company.models.Account;
import com.company.repositories.interfaces.IAccountRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements IAccountRepository {

    private final IDB db;

    public AccountRepository(IDB db) {
        this.db = db;
    }

    @Override
    public void create(Account account) {
        String sql = "INSERT INTO accounts(user_id, name, balance) VALUES (?, ?, ?)";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, account.getUserId());
            ps.setString(2, account.getName());
            ps.setDouble(3, account.getBalance());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    account.setId(keys.getInt(1));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account getById(int id) {
        String sql = "SELECT id, user_id, name, balance FROM accounts WHERE id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new NotFoundException("Account not found: id=" + id);
                }
                return mapToAccount(rs);
            }

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Account> getByUserId(int userId) {
        String sql = "SELECT id, user_id, name, balance FROM accounts WHERE user_id = ? ORDER BY id";
        List<Account> result = new ArrayList<>();

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapToAccount(rs));
                }
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateBalance(int accountId, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, newBalance);
            ps.setInt(2, accountId);

            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new NotFoundException("Account not found for update: id=" + accountId);
            }

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Account mapToAccount(ResultSet rs) throws Exception {
        Account a = new Account();
        a.setId(rs.getInt("id"));
        a.setUserId(rs.getInt("user_id"));
        a.setName(rs.getString("name"));
        a.setBalance(rs.getDouble("balance"));
        return a;
    }
}