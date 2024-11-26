package com.belvinard.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    // CategoryResponse object need to have a list of CategoryDTO

    private List<CategoryDTO> contents;
}