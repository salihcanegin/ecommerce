package com.dolap.ecommerce.service;

import com.dolap.ecommerce.dto.CategoryDto;
import com.dolap.ecommerce.entity.Category;
import com.dolap.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToCategoryDto)
                .collect(Collectors.toList());
    }

    public CategoryDto get(int id) {
        return categoryRepository.findById(id)
                .map(this::convertToCategoryDto)
                .orElse(null);
    }

    public CategoryDto add(CategoryDto categoryDto) {
        categoryDto.setId(0);
        Category category = categoryRepository.save(convertToCategoryEntity(categoryDto));
        return convertToCategoryDto(category);
    }

    public Integer delete(int id) {
        categoryRepository.deleteById(id);
        return id;
    }

    public CategoryDto update(CategoryDto categoryDto) {
        if (categoryRepository.existsById(categoryDto.getId())) {
            categoryRepository.save(convertToCategoryEntity(categoryDto));
            return categoryDto;
        } else {
            return null;
        }
    }

    private CategoryDto convertToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    private Category convertToCategoryEntity(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }
}
