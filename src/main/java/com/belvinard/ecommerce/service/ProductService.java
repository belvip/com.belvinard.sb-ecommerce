package com.belvinard.ecommerce.service;

import com.belvinard.ecommerce.model.Product;
import com.belvinard.ecommerce.payload.ProductDTO;
import com.belvinard.ecommerce.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();
}
