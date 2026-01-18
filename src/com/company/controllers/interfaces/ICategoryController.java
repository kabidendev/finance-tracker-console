package com.company.controllers.interfaces;

public interface ICategoryController {
    void createCategory(int userId, String name, String type);
    void showCategories(int userId, String type);
}
