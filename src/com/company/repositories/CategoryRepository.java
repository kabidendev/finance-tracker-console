package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Category;
import com.company.models.enums.CategoryType;
import com.company.repositories.interfaces.ICategoryRepository;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CategoryRepository implements ICategoryRepository {
    private final IDB db;

    public CategoryRepository(IDB db) {
        this.db = db;
    }



    @Override
    public boolean create(Category entity) {
        String sql = "INSERT INTO categories(user_id, name, type) VALUES (?, ?, ?) RETURNING id";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (entity.getUserId() == null) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, entity.getUserId());
            }
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getType().name());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Category getById(int id) {
        String sql = "SELECT id, user_id, name, type FROM categories WHERE id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Integer userId = (Integer) rs.getObject("user_id");
                    return new Category(
                            rs.getInt("id"),
                            userId,
                            rs.getString("name"),
                            CategoryType.valueOf(rs.getString("type"))
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Category> getByType(Integer userId, CategoryType type) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, user_id, name, type FROM categories WHERE (user_id = ? OR user_id IS NULL) AND type = ? ORDER BY id";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (userId == null) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, userId);
            }
            ps.setString(2, type.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Integer uid = (Integer) rs.getObject("user_id");
                    String name = rs.getString("name");
                    CategoryType t = CategoryType.valueOf(rs.getString("type"));
                    categories.add(new Category(id, uid, name, t));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }
}
