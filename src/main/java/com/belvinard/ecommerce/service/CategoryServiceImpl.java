package com.belvinard.ecommerce.service;

import com.belvinard.ecommerce.exceptions.APIException;
import com.belvinard.ecommerce.exceptions.ResourceNotFoundException;
import com.belvinard.ecommerce.model.Category;
import com.belvinard.ecommerce.payload.CategoryDTO;
import com.belvinard.ecommerce.payload.CategoryResponse;
import com.belvinard.ecommerce.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize) {
       Pageable pageDetails = PageRequest.of(pageNumber, pageSize);
       Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        // Find all the categories in the database
        List<Category> categories = categoryPage.getContent();

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
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        // Prevent duplicate category names
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryFromDb != null)
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists !!!");
       Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }


    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}