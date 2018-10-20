package guru.springframework.services;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.Product;

@Service
@Profile("map")
public class ProductServiceImpl extends AbstractMapService implements ProductService {
	
	@Override
	public Product saveOrUpdate(Product product) {
		return (Product) super.saveOrUpdate(product);
	}

	@Override
	public Product getById(Integer id) {
		return (Product) super.getById(id);
	}

	@Override
	void loadDatabase() {
		database = new HashMap<>();

		Product p1 = new Product();
		p1.setId(1);
		p1.setDescription("Product 1");
		p1.setPrice(new BigDecimal(567.78));
		p1.setImageUrl("http://example.com/product1");

		database.put(1, p1);
	}

}
