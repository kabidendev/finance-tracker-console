package com.company.repositories;

import com.company.models.Category;
import com.company.models.enums.CategoryType;
import com.company.repositories.interfaces.ICategoryRepository;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CategoryRepository implements ICategoryRepository {
    private final Connection conn;

    public CategoryRepository(Connection conn) {
        this.conn = conn;
    }



    @Override
    public boolean create(Category entity) {
        return false;
    }

    @Override
    public Category getById(int id) {

        return null;
    }

    @Override
    public List<Category> getByType(Integer userId, CategoryType type) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, user_id, name, type FROM categories WHERE (user_id = ? OR user_id IS NULL) AND type = ? ORDER BY name";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
