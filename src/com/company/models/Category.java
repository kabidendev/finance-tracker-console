package com.company.models;

import com.company.models.enums.CategoryType;

public class Category {
    private Integer id;
    private Integer userId;
    private String name;
    private CategoryType type;

    public Category(Integer id, Integer userId, String name, CategoryType type) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
    }

    public Category(Integer userId, String name, CategoryType type) {
        this(null, userId, name, type);
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public CategoryType getType() {
        return type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Category{id=" + id + ", userId=" + userId + ", name='" + name + "', type=" + type + "}";
    }
}
