package com.belvinard.ecommerce.service;

import com.belvinard.ecommerce.model.Category;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface  CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);

    String deleteCategory(Long categoryId);


    Category updateCategory(Category category, Long categoryId);
}
