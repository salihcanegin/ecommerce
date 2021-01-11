package com.dolap.ecommerce.service;

import com.dolap.ecommerce.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();

    ProductDto get(int id);

    ProductDto add(ProductDto productDto);

    List<ProductDto> getByCategoryId(Integer id);

    ProductDto update(ProductDto productDto);

    Integer delete(int id);
}
