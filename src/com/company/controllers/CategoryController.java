package com.company.controllers;

import com.company.controllers.interfaces.ICategoryController;
import com.company.models.enums.CategoryType;
import com.company.repositories.interfaces.ICategoryRepository;
import com.company.models.Category;
import java.util.List;

public abstract class CategoryController implements ICategoryController {

    private final ICategoryRepository repo;

    public CategoryController(ICategoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public Object createCategory(Integer userId, String name, String type) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Category name cannot be empty!");
            return false;
        }
        if (type == null) {
            System.out.println("Category type cannot be null!");
            return false;
        }

        Category category = new Category(null, userId, name.trim(), CategoryType.valueOf(type));
        return repo.create(category);
    }

    @Override
    public List<Category> listCategories(Integer userId, CategoryType type) {
        if (type == null) {
            System.out.println("Category type cannot be null!");
            return List.of();
        }
        return repo.getByType(userId, type);
    }
}
