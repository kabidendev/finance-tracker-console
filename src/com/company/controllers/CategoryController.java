package com.company.controllers;

import com.company.controllers.interfaces.ICategoryController;
import com.company.models.Category;
import com.company.models.enums.CategoryType;
import com.company.repositories.interfaces.ICategoryRepository;
import java.util.List;

public class CategoryController implements ICategoryController {

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

        CategoryType categoryType = CategoryType.valueOf(type.trim().toUpperCase());
        Category category = new Category(null, userId, name.trim(), categoryType);
        return repo.create(category);
    }

    @Override
    public List<Category> listCategories(Integer userId, CategoryType type) {
        if (type == null) {
            System.out.println("Category type cannot be null!");
            return java.util.Collections.emptyList();
        }
        return repo.getByType(userId, type);
    }

    @Override
    public void showCategories(int userId, String type) {
        if (type == null || type.trim().isEmpty()) {
            System.out.println("Category type cannot be empty!");
            return;
        }
        CategoryType categoryType = CategoryType.valueOf(type.trim().toUpperCase());
        List<Category> categories = listCategories(userId, categoryType);
        if (categories.isEmpty()) {
            System.out.println("No categories.");
            return;
        }
        for (Category category : categories) {
            System.out.println(category);
        }
    }
}
