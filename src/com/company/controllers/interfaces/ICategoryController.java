package com.company.controllers.interfaces;

import com.company.models.Category;
import com.company.models.User;
import com.company.models.enums.CategoryType;

import java.util.List;

public interface ICategoryController {
    Category createCategory(int userId, String name, String type);
    List<Category> listCategories(int userId, CategoryType type);
    Category createGlobalCategory(User currentUser, String name, CategoryType type);
}
