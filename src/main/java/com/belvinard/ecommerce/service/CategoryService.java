package com.belvinard.ecommerce.service;

import com.belvinard.ecommerce.model.Category;
import com.belvinard.ecommerce.payload.CategoryDTO;
import com.belvinard.ecommerce.payload.CategoryResponse;

import java.util.List;

public interface  CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);


    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
