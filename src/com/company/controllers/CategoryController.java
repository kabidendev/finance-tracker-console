package com.company.controllers;

import com.company.controllers.interfaces.ICategoryController;
import com.company.exceptions.AccessDeniedException;
import com.company.models.Category;
import com.company.models.User;
import com.company.models.enums.CategoryType;
import com.company.models.enums.Role;

public class CategoryController implements ICategoryController {
    @Override
    public Category createGlobalCategory(User currentUser, String name, CategoryType type) {
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Access denied");
        }
        return new Category(null, name, type);
    }
}
