package com.company.controllers.interfaces;
import com.company.models.Category;
import com.company.models.enums.CategoryType;
import java.util.List;
public interface ICategoryController {
    Object createCategory(Integer userId, String name, String type);
    List<Category> listCategories(Integer userId, CategoryType type);

    void showCategories(int userId, String income);
}