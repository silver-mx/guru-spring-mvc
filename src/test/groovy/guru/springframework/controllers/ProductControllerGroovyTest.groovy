package guru.springframework.controllers


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;

@RunWith(SpringRunner)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerGroovyTest extends GroovyTestCase {

	@Autowired
	private MockMvc mockMvc

	@SpyBean
	private ProductService productService

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this)
	}

	@Test
	void testListProducts() {
		List<Product> expectedProducts = new ArrayList<>()
		expectedProducts.add(new Product())
		expectedProducts.add(new Product())

		when(productService.listAll()).thenReturn(expectedProducts)

		mockMvc.perform(get("/products")).andExpect(status().isOk()).andExpect(view().name("products"))
				.andExpect(model().attribute("products", hasSize(2)))
				
	}
}
