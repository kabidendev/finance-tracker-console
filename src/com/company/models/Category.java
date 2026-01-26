package com.company.models;

import com.company.models.enums.CategoryType;

public class Category {
    private int id;
    private Integer userId;
    private String name;
    private CategoryType type;

    public Category() {
    }

    public Category(Integer userId, String name, CategoryType type) {
        this.userId = userId;
        this.name = name;
        this.type = type;
    }

    public Category(int id, Integer userId, String name, CategoryType type) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Category{id=" + id + ", userId=" + userId + ", name='" + name + "', type=" + type + "}";
    }
}
