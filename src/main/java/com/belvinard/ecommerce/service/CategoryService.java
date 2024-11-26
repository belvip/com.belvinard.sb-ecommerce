package com.belvinard.ecommerce.service;

import com.belvinard.ecommerce.model.Category;
import com.belvinard.ecommerce.payload.CategoryResponse;

import java.util.List;

public interface  CategoryService {
    CategoryResponse getAllCategories();
    void createCategory(Category category);

    String deleteCategory(Long categoryId);


    Category updateCategory(Category category, Long categoryId);
}
