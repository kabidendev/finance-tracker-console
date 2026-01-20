package com.company.models;

import com.company.models.enums.CategoryType;

public class Category {
    private int id;
    private Integer userid;
    private String name;
    private CategoryType type;

    public Category (Integer id, Integer userid, String name, CategoryType type){
        this.id = id;
        this.userid = userid;
        this.name = name;
        this.type = type;
    }

    public Category(Integer userId, String name, CategoryType type) {
        this(null, userId, name, type);
    }

    public Category(int i, Integer userid, String name, CategoryType type) {

    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;}

    public Integer getUserId(Integer userId){
        return userId;
    }

    public void setUserId(Integer userId){
        this.userid = userid;}

    public String getName(String name){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public CategoryType getType(CategoryType type){
        return type;
    }

    public void setType(CategoryType type){
        this.type = type;}

    @Override
    public String toString(){
        return "Category{" +
            "id=" + id +
            "userid=" + userid +
            "Name=" + name + '\'' +
            "Type=" + type +
            '}';
    }
}
