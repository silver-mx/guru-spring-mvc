package guru.springframework.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.Product;

@Service
@Profile("jpa")
@Transactional
public class ProductServiceJpaDaoImpl implements ProductService {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<?> listAll() {
		List<Product> products = em.createQuery("from Product", Product.class).getResultList();
		return products;
	}

	@Override
	public Product getById(Integer id) {
		Product product =  em.find(Product.class, id);
		return product;
	}

	@Override
	public Product saveOrUpdate(Product domainObject) {
		Product saved = em.merge(domainObject);
		return saved;
	}

	@Override
	public void delete(Integer id) {
		Product product = em.find(Product.class, id);
		em.remove(product);
	}

}
