package com.company.controllers.interfaces;

import com.company.models.Category;
import com.company.models.User;
import com.company.models.enums.CategoryType;

public interface ICategoryController {
    Category createGlobalCategory(User currentUser, String name, CategoryType type);
}
