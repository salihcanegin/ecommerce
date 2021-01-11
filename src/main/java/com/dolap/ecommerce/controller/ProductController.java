package com.dolap.ecommerce.controller;

import com.dolap.ecommerce.dto.ProductDto;
import com.dolap.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDto> getAll() {
        return productService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ProductDto get(@PathVariable int id) {
        return productService.get(id);
    }

    @GetMapping(value = "/category/{id}")
    public List<ProductDto> getByCategoryId(@PathVariable Integer id) {
        return productService.getByCategoryId(id);
    }

    @PostMapping
    public ProductDto add(@RequestBody ProductDto productDto) {
        return productService.add(productDto);
    }

    @DeleteMapping(value = "/{id}")
    public Integer delete(@PathVariable int id) {
        return productService.delete(id);
    }

    @PutMapping
    public ProductDto update(@RequestBody ProductDto productDto) {
        return productService.update(productDto);
    }
}
