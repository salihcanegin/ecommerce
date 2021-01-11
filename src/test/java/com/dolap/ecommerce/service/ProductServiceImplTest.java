package com.dolap.ecommerce.service;

import com.dolap.ecommerce.dto.ProductDto;
import com.dolap.ecommerce.entity.Category;
import com.dolap.ecommerce.entity.Product;
import com.dolap.ecommerce.repository.CategoryRepository;
import com.dolap.ecommerce.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductServiceImplTest {
    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public ProductService productService() {
            return new ProductServiceImpl();
        }
    }

    @Autowired
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        Product productTable = new Product();
        productTable.setId(1);
        productTable.setName("table");
        productTable.setDescription("long");
        productTable.setPrice(10);

        Product productChair = new Product();
        productChair.setId(2);
        productChair.setName("chair");
        productChair.setDescription("short");
        productChair.setPrice(50);

        Category categoryHome = new Category(1, "home");
        categoryHome.setProducts(Arrays.asList(productTable, productChair));
        productTable.setCategory(categoryHome);
        productTable.setCategory(categoryHome);

        when(productRepository.findById(1))
                .thenReturn(Optional.of(productTable));
        when(productRepository.findById(2))
                .thenReturn(Optional.of(productChair));
        when(categoryRepository.findById(1))
                .thenReturn(Optional.of(categoryHome));

        Product productBall = new Product();
        productBall.setId(3);
        productBall.setName("ball");
        productBall.setDescription("heavy");
        productBall.setPrice(40);

        Category categorySport = new Category(2, "sport");
        categoryHome.setProducts(Collections.singletonList(productBall));
        productBall.setCategory(categorySport);

        when(productRepository.findById(3))
                .thenReturn(Optional.of(productBall));
        when(categoryRepository.findById(2))
                .thenReturn(Optional.of(categorySport));
        when(productRepository.findByCategoryId(2))
                .thenReturn(Collections.singletonList(productBall));
        when(productRepository.findAll())
                .thenReturn(Arrays.asList(productTable, productChair, productBall));
    }

    @Test
    public void whenValidId_thenReturnProductDto() {
        int id = 1;
        ProductDto productDto = productService.get(1);
        assertThat(productDto.getId())
                .isEqualTo(id);
    }

    @Test
    public void whenFindAll_thenReturnAllProductDtos() {
        List<ProductDto> allProducts = productService.findAll();
        assertThat(allProducts.size())
                .isEqualTo(3);
    }

    @Test
    public void whenFindByCategoryId_thenReturnRelatedProductDtos() {
        List<ProductDto> productDtos = productService.getByCategoryId(2);
        assertThat(productDtos.size())
                .isEqualTo(1);
        assertThat(productDtos.iterator().next().getName())
                .isEqualTo("ball");
    }

}
