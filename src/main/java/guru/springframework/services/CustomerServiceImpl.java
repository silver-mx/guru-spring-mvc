package guru.springframework.services;

import org.springframework.stereotype.Service;

import guru.springframework.domain.Customer;

@Service
public class CustomerServiceImpl extends AbstractMapService implements CustomerService {

	protected void loadDatabase() {
		Customer c1 = new Customer();
		c1.setId(1);
		c1.setFirstName("Diego");
		c1.setLastName("Núñez Silva");
		c1.setAddressLineOne("Line 132");
		c1.setEmail("diego.nunez.mx@gmail.com");
		c1.setPhone("777777777777");
		c1.setCity("Somewhere");
		c1.setState("State");
		c1.setZipCode("12345");

		database.put(c1.getId(), c1);
	}

	@Override
	public Customer getById(Integer id) {
		return (Customer) super.getById(id);
	}

	@Override
	public Customer saveOrUpdate(Customer domainObject) {
		return (Customer) super.saveOrUpdate(domainObject);
	}
}
