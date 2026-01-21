package com.company.repositories.interfaces;

import com.company.models.Category;
import com.company.models.enums.CategoryType;


import java.util.List;

public interface ICategoryRepository extends IRepository<Category> {
List<Category> getByType(Integer userId, CategoryType type) ;
}
