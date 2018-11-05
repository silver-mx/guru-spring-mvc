package guru.springframework.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Product;

@Transactional
@Rollback(true)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { JpaIntegrationConfig.class })
@ActiveProfiles({ "jpa", "dev" })
class ProductServiceJpaDaoImplTest {

	private static final Logger log = LoggerFactory.getLogger(ProductServiceJpaDaoImplTest.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private Environment env;
	
	private Product product;
	
	//@BeforeAll
	@BeforeEach
	public void before() {	
		log.info("Environment=" + env.getProperty("app.name"));
		log.info("BEFORE: Products in db={}", productService.listAll().size());
		product = productService.saveOrUpdate(createProduct());
	}
	
	//@AfterAll
	@AfterEach
	public void after() {
		log.info("AFTER: Products in db={}", productService.listAll().size());
	}

	@Test
	void testListAll() {
		@SuppressWarnings("unchecked")
		List<Product> products = (List<Product>) productService.listAll();
		assertThat(products.size()).isEqualTo(2);
	}

	@Test
	void testGetById() {
		Product productLoaded = productService.getById(product.getId());
		assertThat(productLoaded).isEqualTo(product);
	}

	@Test
	void testSaveOrUpdate() {
		Product product = createProduct();
		assertThat(product.getId()).isNull();
		product = productService.saveOrUpdate(product);
		assertThat(product.getId()).isNotNull();
		assertThat(product.getOptimisticLockingVersion()).isNotNull();
	}

	private Product createProduct() {
		Product product = new Product();
		product.setDescription("Test Description");
		product.setImageUrl("http://fake.url");
		product.setPrice(new BigDecimal("10.99"));

		return product;
	}

	@Test
	void testDelete() {
		productService.delete(product.getId());
		assertThat(productService.getById(product.getId())).isNull();
	}

}
