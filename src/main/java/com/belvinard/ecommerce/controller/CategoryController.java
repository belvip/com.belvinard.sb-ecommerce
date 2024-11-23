package com.belvinard.ecommerce.controller;

// Import statements
import com.belvinard.ecommerce.model.Category; // Represents the Category model.
import com.belvinard.ecommerce.service.CategoryService; // The service layer for managing category-related operations.
import org.springframework.beans.factory.annotation.Autowired; // Used for dependency injection.
import org.springframework.web.bind.annotation.GetMapping; // Maps HTTP GET requests.
import org.springframework.web.bind.annotation.PostMapping; // Maps HTTP POST requests.
import org.springframework.web.bind.annotation.RequestBody; // Maps HTTP request body to a method parameter.
import org.springframework.web.bind.annotation.RestController; // Indicates that this class is a REST controller.

import java.util.List; // Used to represent a collection of Category objects.

@RestController // Marks this class as a controller that handles RESTful web requests.
public class CategoryController {

    @Autowired // Automatically injects the required dependency (CategoryService).
    private CategoryService categoryService; // Service layer for handling business logic related to categories.

    // Constructor for injecting the CategoryService dependency (useful for testing and more explicit DI).
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Handles HTTP GET requests to fetch all categories.
     * Endpoint: /api/public/categories
     *
     * @return A list of all Category objects managed by the service layer.
     */
    @GetMapping("api/public/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories(); // Delegates the call to the service layer.
    }

    /**
     * Handles HTTP POST requests to create a new category.
     * Endpoint: /api/public/categories
     *
     * @param category The Category object sent in the request body.
     * @return A success message confirming that the category has been added.
     */
    @PostMapping("api/public/categories")
    public String createCategory(@RequestBody Category category) {
        categoryService.createCategory(category); // Delegates the creation logic to the service layer.
        return "Category added successfully"; // Returns a confirmation message.
    }
}
