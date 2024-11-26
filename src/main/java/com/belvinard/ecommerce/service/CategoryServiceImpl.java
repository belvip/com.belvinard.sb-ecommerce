package com.belvinard.ecommerce.service;

import com.belvinard.ecommerce.exceptions.APIException;
import com.belvinard.ecommerce.exceptions.ResourceNotFoundException;
import com.belvinard.ecommerce.model.Category;
import com.belvinard.ecommerce.payload.CategoryDTO;
import com.belvinard.ecommerce.payload.CategoryResponse;
import com.belvinard.ecommerce.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{
    //private List<Category> categories = new ArrayList<>();
    //private Long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves all the categories from the database and maps them to CategoryDTO objects.
     *
     * @return A {@link CategoryResponse} object containing a list of all categories.
     * @throws APIException If no categories are found in the database.
     */
    @Override
    public CategoryResponse getAllCategories() {
        // Find all the categories in the database
        List<Category> categories = categoryRepository.findAll();

        // If no categories are found, throw an APIException
        if (categories.isEmpty()) {
            throw new APIException("No category created till now.");
        }

        // Map each Category entity to a CategoryDTO using the ModelMapper
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        // Create a CategoryResponse object and set its contents with the mapped DTOs
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContents(categoryDTOS);

        // Return the CategoryResponse containing all the mapped CategoryDTOs
        return categoryResponse;
    }


    @Override
    public void createCategory(Category category) {
        // Prevent duplicate category names
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null)
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists !!!");
        //category.setCategoryId(nextId++);
        categoryRepository.save(category);
    }


    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        categoryRepository.delete(category);
        return "Category with categoryId: " + categoryId + " deleted successfully !!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return savedCategory;
    }
}