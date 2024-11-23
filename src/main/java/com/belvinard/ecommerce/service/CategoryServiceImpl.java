package com.belvinard.ecommerce.service;

import com.belvinard.ecommerce.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {


    private List<Category> categories = new ArrayList<>(); // In-memory storage for categories.
    private Long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++); // Assign the next available ID and increment.
        categories.add(category);

    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories.stream()
                .filter(c ->c.getCategoryId().equals(categoryId))
                .findFirst().orElse(null);
        if (category == null){
            return "Category not found";
        }
        categories.remove(category);
        return "Category with categoryId : " + categoryId + " deleted successfully";

    }

    // findFirst() matches with get() method


}
