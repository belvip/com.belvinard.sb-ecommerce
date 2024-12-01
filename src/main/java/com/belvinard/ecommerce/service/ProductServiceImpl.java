package com.belvinard.ecommerce.service;

import com.belvinard.ecommerce.exceptions.APIException;
import com.belvinard.ecommerce.exceptions.ResourceNotFoundException;
import com.belvinard.ecommerce.model.Category;
import com.belvinard.ecommerce.model.Product;
import com.belvinard.ecommerce.payload.ProductDTO;
import com.belvinard.ecommerce.payload.ProductResponse;
import com.belvinard.ecommerce.repositories.CategoryRepository;
import com.belvinard.ecommerce.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Service // use for business logic
public class ProductServiceImpl implements ProductService  {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    /* ================================================ ADD PRODUCT ================================================ */
    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {

        Category category = categoryRepository.findById(categoryId) // If category exist, return the category
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "categoryId", categoryId));

        // ======== Check if product already present or not ========
        /* Check if product exists into the database */
        boolean isProductNoPresent = true;

        List<Product> products = category.getProducts();
        for (Product value : products) {
            if (value.getProductName().equals(productDTO.getProductName())) {
                isProductNoPresent = false;
                break;
            }
        }

        if(isProductNoPresent) {
            Product product = modelMapper.map(productDTO, Product.class);
            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice() -
                    ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);

            // Return the productDTO
            return modelMapper.map(savedProduct, ProductDTO.class);

        }else {
            throw new APIException("Product already exist !!");
        }

    }

    /* ================================================ GET ALL PRODUCT ================================================ */
    @Override
    public ProductResponse getAllProducts() {
        // Check if products size is 0
        // Fetch all products from the repository
        List<Product> products = productRepository.findAll();

        // Check if the list of products is empty
        if (products.isEmpty()) {
            throw new APIException("No products have been added yet.");
        }

        // Map products to ProductDTO
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        // Create and return the ProductResponse
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    /* ================================================ GET PRODUCT BY CATEGORY ================================================ */
    @Override
    public ProductResponse searchByCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId) // If category existing, return the category
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    /* ================================================ SEARCH PRODUCT BY KEYWORD ================================================ */

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        // products size is 0
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    /* ================================================ UPDATE PRODUCT ================================================ */
    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {

        // Get the existing product from the database
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "product", productId));

        Product product = modelMapper.map(productDTO, Product.class);
        // Update the product info with one in request body
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setSpecialPrice(product.getSpecialPrice());

        // Save to database
        Product savedProduct = productRepository.save(productFromDb);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    /* ================================================ DELETE PRODUCT ================================================ */
    @Override
    public ProductDTO deleteProduct(Long productId) {
        // Get the existing product from the database
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        // Delete product to the database
       productRepository.delete(product);

        return modelMapper.map(product, ProductDTO.class);
    }

    /* ================================================ UPDATE PRODUCT IMAGE ================================================ */
    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        // Get the product from DB
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        // Upload image to server
        // Get the file name of uploaded image
        //String path = "images/";
        String fileName = fileService.uploadImage(path, image);

        // Updating the new file name to the product
        productFromDb.setImage(fileName);

        // save the updated product
        Product updatedProduct = productRepository.save(productFromDb);

        // return DTO after mapping product to DTO
        return modelMapper.map(updatedProduct, ProductDTO.class);

    }




}
