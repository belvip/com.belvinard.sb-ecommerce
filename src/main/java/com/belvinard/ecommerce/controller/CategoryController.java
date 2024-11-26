package com.belvinard.ecommerce.controller;

// Import statements
import com.belvinard.ecommerce.model.Category; // Represents the Category model.
import com.belvinard.ecommerce.payload.CategoryDTO;
import com.belvinard.ecommerce.payload.CategoryResponse;
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
    public ResponseEntity<CategoryResponse> getAllCategories(
            // Accepting page number
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize)  {
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK); // Delegates the call to the service layer.
    }


    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.createCategory(categoryDTO); // Delegates the creation logic to the service layer.
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED); // Returns a confirmation message.
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO>  deleteCategory(@PathVariable("categoryId") Long categoryId){
        // Implement ResponsesEntity
        CategoryDTO deleteCategory = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deleteCategory, HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid  @PathVariable Long categoryId,
                                                 @RequestBody CategoryDTO categoryDTO) {

        CategoryDTO savedCategoryDTO =  categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);


    }
}
