package com.belvinard.ecommerce.controller;

// Import statements
import com.belvinard.ecommerce.model.Category; // Represents the Category model.
import com.belvinard.ecommerce.service.CategoryService; // The service layer for managing category-related operations.
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired; // Used for dependency injection.
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List; // Used to represent a collection of Category objects.

@RestController // Marks this class as a controller that handles RESTful web requests.
@RequestMapping("/api")
public class CategoryController {

    @Autowired // Automatically injects the required dependency (CategoryService).
    private CategoryService categoryService; // Service layer for handling business logic related to categories.

    // Constructor for injecting the CategoryService dependency (useful for testing and more explicit DI).
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/public/categories")
    public ResponseEntity<List<Category> > getAllCategories() {
        List<Category> allCategories = categoryService.getAllCategories();
        return new ResponseEntity<>(allCategories, HttpStatus.OK); // Delegates the call to the service layer.
    }


    @PostMapping("/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category); // Delegates the creation logic to the service layer.
        return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED); // Returns a confirmation message.
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String>  deleteCategory(@PathVariable("categoryId") Long categoryId){
        // Implement ResponsesEntity
        String status = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@Valid @PathVariable Long categoryId,
                                                 @RequestBody Category category) {

        Category savedCategory =  categoryService.updateCategory(category, categoryId);
        return new ResponseEntity<>("Category with category id : " + categoryId, HttpStatus.OK);


    }
}
