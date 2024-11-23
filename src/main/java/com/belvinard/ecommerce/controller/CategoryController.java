package com.belvinard.ecommerce.controller;

// Import statements
import com.belvinard.ecommerce.model.Category; // Represents the Category model.
import com.belvinard.ecommerce.service.CategoryService; // The service layer for managing category-related operations.
import org.springframework.beans.factory.annotation.Autowired; // Used for dependency injection.
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List; // Used to represent a collection of Category objects.

@RestController // Marks this class as a controller that handles RESTful web requests.
public class CategoryController {

    @Autowired // Automatically injects the required dependency (CategoryService).
    private CategoryService categoryService; // Service layer for handling business logic related to categories.

    // Constructor for injecting the CategoryService dependency (useful for testing and more explicit DI).
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("api/public/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories(); // Delegates the call to the service layer.
    }


    @PostMapping("api/public/categories")
    public String createCategory(@RequestBody Category category) {
        categoryService.createCategory(category); // Delegates the creation logic to the service layer.
        return "Category added successfully"; // Returns a confirmation message.
    }

    @DeleteMapping("api/admin/categories/{categoryId}")
    public ResponseEntity<String>  deleteCategory(@PathVariable("categoryId") Long categoryId){
        // Implement ResponsesEntity
        try {
            String status = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }

    }
}
