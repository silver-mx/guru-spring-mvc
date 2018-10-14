package guru.springframework.controllers;



import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@SpyBean
	private ProductService productService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testListAll() throws Exception {
		List<Product> expectedProducts = new ArrayList<>();
		expectedProducts.add(new Product());
		expectedProducts.add(new Product());

		when(productService.listAllProducts()).thenReturn(expectedProducts);

		mockMvc.perform(get("/products")).andExpect(status().isOk()).andExpect(view().name("products"))
				.andExpect(model().attribute("products", hasSize(2)));
	}

	@Test
	public void testDelete() throws Exception {

		mockMvc.perform(get("/product/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/products"));

		verify(productService, times(1)).delete(1);
	}

	@Test
	public void testCreate() throws Exception {
		mockMvc.perform(get("/product/new")).andExpect(status().isOk()).andExpect(view().name("productForm"))
				.andExpect(model().attribute("product", equalTo(new Product())));

		verifyZeroInteractions(productService);
	}

	@Test
	public void testSaveOrUpdate() throws Exception {

		Product expectedProduct = new Product();
		expectedProduct.setId(1);
		expectedProduct.setDescription("A product");
		expectedProduct.setImageUrl("http://animage.se");
		expectedProduct.setPrice(new BigDecimal("109.99"));
		
		doReturn(expectedProduct).when(productService).saveOrUpdate(any());

		mockMvc.perform(post("/product").param("id", "1").param("description", "A product")
				.param("imageUrl", "http://animage.se").param("price", "109.99")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/product/1"));
		
		ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
		verify(productService).saveOrUpdate(productCaptor.capture());
		assertEquals(expectedProduct.getId(), productCaptor.getValue().getId());
		assertEquals(expectedProduct.getDescription(), productCaptor.getValue().getDescription());
		assertEquals(expectedProduct.getPrice(), productCaptor.getValue().getPrice());
		assertEquals(expectedProduct.getImageUrl(), productCaptor.getValue().getImageUrl());
	}

}
