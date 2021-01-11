package com.dolap.ecommerce.controller;

import com.dolap.ecommerce.dto.CategoryDto;
import com.dolap.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryService.findAll();
    }

    @GetMapping(value = "/{id}")
    public CategoryDto get(@PathVariable int id) {
        return categoryService.get(id);
    }

    @PostMapping
    public CategoryDto add(@RequestBody CategoryDto categoryDto) {
        return categoryService.add(categoryDto);
    }

    @DeleteMapping(value = "/{id}")
    public Integer delete(@PathVariable int id) {
        return categoryService.delete(id);
    }

    @PutMapping
    public CategoryDto update(@RequestBody CategoryDto categoryDto) {
        return categoryService.update(categoryDto);
    }
}
