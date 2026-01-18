package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.User;
import com.company.models.enums.Role;
import com.company.repositories.interfaces.IUserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository implements IUserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public int create(User user) {
        String sql = "INSERT INTO users(name, email, password, role) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection con = db.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole().name());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    user.setId(id);
                    return id;
                }
            }
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByEmail(String email) {
        String sql = "SELECT id, name, email, password, role FROM users WHERE email = ?";
        try (Connection con = db.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String mail = rs.getString("email");
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    return new User(id, name, mail, password, Role.valueOf(role));
                }
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(int id) {
        String sql = "SELECT id, name, email, password, role FROM users WHERE id = ?";
        try (Connection con = db.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("id");
                    String name = rs.getString("name");
                    String mail = rs.getString("email");
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    return new User(userId, name, mail, password, Role.valueOf(role));
                }
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
