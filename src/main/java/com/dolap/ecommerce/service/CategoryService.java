package com.dolap.ecommerce.service;

import com.dolap.ecommerce.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();

    CategoryDto get(int id);

    CategoryDto add(CategoryDto categoryDto);

    Integer delete(int id);

    CategoryDto update(CategoryDto categoryDto);
}
