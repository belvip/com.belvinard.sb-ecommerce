package com.belvinard.ecommerce.service;

import com.belvinard.ecommerce.model.Product;
import com.belvinard.ecommerce.payload.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
}
