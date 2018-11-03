package guru.springframework.bootstrap;

import java.math.BigDecimal;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private final ProductService productService;

	public SpringJpaBootstrap(ProductService productService) {
		super();
		this.productService = productService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		loadProducts();
	}

	private void loadProducts() {
		Product p1 = new Product();
		p1.setId(1);
		p1.setDescription("Product 1 Production");
		p1.setPrice(new BigDecimal(567.78));
		p1.setImageUrl("http://example.com/product1");

		productService.saveOrUpdate(p1);
	}

}
