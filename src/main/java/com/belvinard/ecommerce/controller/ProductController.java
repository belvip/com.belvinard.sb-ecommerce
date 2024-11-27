package com.belvinard.ecommerce.controller;

import com.belvinard.ecommerce.model.Product;
import com.belvinard.ecommerce.payload.ProductDTO;
import com.belvinard.ecommerce.payload.ProductResponse;
import com.belvinard.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product,
                                                 @PathVariable Long categoryId){
        ProductDTO productDTO = productService.addProduct(categoryId, product);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);

    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
        ProductResponse productResponse =  productService.getAllProducts();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
}
