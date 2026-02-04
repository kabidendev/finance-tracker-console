package com.company.controllers;

import com.company.controllers.interfaces.ICategoryController;
import com.company.exceptions.AccessDeniedException;
import com.company.exceptions.ValidationException;
import com.company.models.Category;
import com.company.models.User;
import com.company.models.enums.CategoryType;
import com.company.models.enums.Role;
import com.company.repositories.interfaces.ICategoryRepository;

import java.util.List;

public class CategoryController implements ICategoryController {
    private final ICategoryRepository categoryRepository;

    public CategoryController(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(int userId, String name, String type) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Category name cannot be empty");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new ValidationException("Category type cannot be empty");
        }

        CategoryType categoryType;
        try {
            categoryType = CategoryType.valueOf(type.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid category type");
        }

        Category category = new Category(userId, name.trim(), categoryType);
        if (!categoryRepository.create(category)) {
            throw new RuntimeException("Failed to create category");
        }
        return category;
    }

    @Override
    public List<Category> listCategories(int userId, CategoryType type) {
        return categoryRepository.getByType(userId, type);
    }

    @Override
    public Category createGlobalCategory(User currentUser, String name, CategoryType type) {
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Access denied");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Category name cannot be empty");
        }

        Category category = new Category(null, name, type);
        if (!categoryRepository.create(category)) {
            throw new RuntimeException("Failed to create category");
        }
        return category;
    }
}
