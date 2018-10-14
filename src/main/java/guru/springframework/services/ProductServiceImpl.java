package guru.springframework.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import guru.springframework.domain.Product;

@Service
public class ProductServiceImpl implements ProductService {

	private Map<Integer, Product> products;

	public ProductServiceImpl() {
		loadProducts();
	}

	@Override
	public List<Product> listAllProducts() {
		return new ArrayList<>(products.values());
	}

	private void loadProducts() {
		products = new HashMap<>();

		Product p1 = new Product();
		p1.setId(1);
		p1.setDescription("Product 1");
		p1.setPrice(new BigDecimal(567.78));
		p1.setImageUrl("http://example.com/product1");

		products.put(1, p1);
	}

	@Override
	public Product getProductById(Integer id) {
		return products.get(id);
	}

	@Override
	public Product saveOrUpdate(Product product) {

		if (product != null) {
			if (product.getId() == null) {
				product.setId(getNextKey());
			}
			products.put(product.getId(), product);
		} else {
			throw new IllegalArgumentException("The product cannot be null");
		}

		return product;
	}

	private Integer getNextKey() {
		return Collections.max(products.keySet()) + 1;
	}

	@Override
	public void delete(Integer id) {
		products.remove(id);
	}

}
