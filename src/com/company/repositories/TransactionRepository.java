package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Transaction;
import com.company.models.enums.TransactionType;
import com.company.repositories.interfaces.ITransactionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements ITransactionRepository {

    private final IDB db;

    public TransactionRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean create(Transaction t) {
        String sql =
                "INSERT INTO transactions(user_id, type, amount, category_id, account_from_id, account_to_id, comment) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, t.getUserId());
            st.setString(2, t.getType().name());
            st.setDouble(3, t.getAmount());


            if (t.getCategoryId() == null) st.setNull(4, Types.INTEGER);
            else st.setInt(4, t.getCategoryId());

            if (t.getAccountFromId() == null) st.setNull(5, Types.INTEGER);
            else st.setInt(5, t.getAccountFromId());

            if (t.getAccountToId() == null) st.setNull(6, Types.INTEGER);
            else st.setInt(6, t.getAccountToId());


            if (t.getComment() == null) st.setNull(7, Types.VARCHAR);
            else st.setString(7, t.getComment());

            return st.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("create Transaction error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Transaction getById(int id) {
        String sql =
                "SELECT id, user_id, type, amount, category_id, account_from_id, account_to_id, created_at, comment " +
                        "FROM transactions WHERE id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) return mapTransaction(rs);
            }

        } catch (SQLException e) {
            System.out.println("getById Transaction error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Transaction> getByUserId(int userId) {
        String sql =
                "SELECT id, user_id, type, amount, category_id, account_from_id, account_to_id, created_at, comment " +
                        "FROM transactions WHERE user_id = ? ORDER BY id";

        List<Transaction> list = new ArrayList<>();

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, userId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(mapTransaction(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("getByUserId Transaction error: " + e.getMessage());
        }

        return list;
    }

    private Transaction mapTransaction(ResultSet rs) throws SQLException {
        Integer categoryId = null;
        int cat = rs.getInt("category_id");
        if (!rs.wasNull()) categoryId = cat;

        Integer fromId = null;
        int af = rs.getInt("account_from_id");
        if (!rs.wasNull()) fromId = af;

        Integer toId = null;
        int at = rs.getInt("account_to_id");
        if (!rs.wasNull()) toId = at;

        return new Transaction(
                rs.getInt("id"),
                rs.getInt("user_id"),
                TransactionType.valueOf(rs.getString("type")),
                rs.getDouble("amount"),
                categoryId,
                fromId,
                toId,
                rs.getTimestamp("created_at"),
                rs.getString("comment")
        );
    }
}

