package guru.springframework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping("/customers")
	public String listAllCustomers(Model model) {
		model.addAttribute("customers", customerService.listAll());
		return "customer/customers";
	}
	
	@RequestMapping("/customer/{id}")
	public String getProductById(@PathVariable Integer id, Model model) {
		model.addAttribute("customer", customerService.getById(id));
		return "customer/customer";
	}
	
	@RequestMapping("/customer/new")
	public String createCustomer(Model model) {
		model.addAttribute("customer", new Customer());
		return "customer/newOrEditCustomer";
	}
	
	@RequestMapping("/customer/edit/{id}")
	public String editCustomer(@PathVariable Integer id, Model model) {
		model.addAttribute("customer", customerService.getById(id));
		return "customer/newOrEditCustomer";
	}
	
	@RequestMapping(value ="/customer", method=RequestMethod.POST)
	public String saveOrUpdateCustomer(Customer customer) {
		Customer updatedCustomer = customerService.saveOrUpdate(customer);
		return "redirect:/customer/" + updatedCustomer.getId();
	}
	
	@RequestMapping("/customer/delete/{id}")
	public String deleteCustomer(@PathVariable Integer id) {
		customerService.delete(id);
		return "redirect:/customers";
	}

}
