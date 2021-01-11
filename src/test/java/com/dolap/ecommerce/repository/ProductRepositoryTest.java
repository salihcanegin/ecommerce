package com.dolap.ecommerce.repository;

import com.dolap.ecommerce.entity.Category;
import com.dolap.ecommerce.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void whenFindById_thenReturnProduct() {
        Product product = createProduct("table", "long", 10, null);
        Product persistedProduct = entityManager.persist(product);
        Product found = productRepository.findById(persistedProduct.getId()).orElse(new Product());

        assertThat(found.getName())
                .isEqualTo(product.getName());
    }

    @Test
    public void whenFindAll_thenReturnAllProducts() {
        Product productTable = createProduct("table", "long", 10, null);
        Product productChair = createProduct("chair", "medium", 20, null);
        entityManager.persist(productTable);
        entityManager.persist(productChair);

        assertThat(productRepository.findAll().size())
                .isEqualTo(2);
    }

    @Test
    public void whenFindByCategoryId_thenReturnRelatedProducts() {
        Category categoryHome = new Category();
        categoryHome.setName("home");
        Category persistedCategoryHome = entityManager.persist(categoryHome);
        Product productTable = createProduct("table", "long", 10, persistedCategoryHome);
        Product productChair = createProduct("chair", "medium", 20, persistedCategoryHome);
        persistedCategoryHome.getProducts().add(productTable);
        persistedCategoryHome.getProducts().add(productChair);
        entityManager.persist(productTable);
        entityManager.persist(productChair);

        Category categorySport = new Category();
        categorySport.setName("sport");
        Category persistedCategorySport = entityManager.persist(categorySport);
        Product productBall = createProduct("ball", "heavy", 150, persistedCategorySport);
        persistedCategorySport.getProducts().add(productBall);
        entityManager.persist(productBall);

        List<Product> products = productRepository.findByCategoryId(persistedCategorySport.getId());
        assertThat(products.size())
                .isEqualTo(1);
        assertThat(products.iterator().next().getName())
                .isEqualTo(productBall.getName());
    }

    private Product createProduct(String name, String description, double price, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        return product;
    }
}
