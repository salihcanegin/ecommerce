package com.dolap.ecommerce.service;

import com.dolap.ecommerce.dto.ProductDto;
import com.dolap.ecommerce.entity.Product;
import com.dolap.ecommerce.repository.CategoryRepository;
import com.dolap.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToProductDto)
                .collect(Collectors.toList());
    }

    public ProductDto get(int id) {
        return productRepository.findById(id)
                .map(this::convertToProductDto)
                .orElse(null);
    }

    public ProductDto add(ProductDto productDto) {
        productDto.setId(0);
        Product product = productRepository.save(convertToProductEntity(productDto));
        return convertToProductDto(product);
    }

    public List<ProductDto> getByCategoryId(Integer id) {
        return productRepository.findByCategoryId(id)
                .stream()
                .map(this::convertToProductDto)
                .collect(Collectors.toList());
    }

    public ProductDto update(ProductDto productDto) {
        if (productRepository.existsById(productDto.getId())) {
            productRepository.save(convertToProductEntity(productDto));
            return productDto;
        } else {
            return null;
        }
    }

    public Integer delete(int id) {
        productRepository.deleteById(id);
        return id;
    }

    private Product convertToProductEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        //if categoryId is invalid, generate product without category
        categoryRepository.findById(productDto.getCategoryId())
                .ifPresent(category -> {
                    product.setCategory(category);
                    category.getProducts().add(product);
                });
        return product;
    }

    private ProductDto convertToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        Optional.ofNullable(product.getCategory())
                .ifPresent(category -> productDto.setCategoryId(category.getId()));
        return productDto;
    }
}
