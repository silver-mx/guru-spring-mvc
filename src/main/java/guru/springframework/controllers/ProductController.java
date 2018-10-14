package guru.springframework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping("/products")
	public String listProducts(Model model) {

		model.addAttribute("products", productService.listAllProducts());

		return "products";
	}

	@RequestMapping("/product/{id}")
	public String getProduct(@PathVariable Integer id, Model model) {

		model.addAttribute("product", productService.getProductById(id));
		return "product";
	}

	@RequestMapping("/product/new")
	public String newProduct(Model model) {
		model.addAttribute("product", new Product());
		return "productForm";
	}

	@RequestMapping("/product/edit/{id}")
	public String editProduct(@PathVariable Integer id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return "productForm";
	}

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public String saveOrUpdateProduct(Product product) {
		Product savedProduct = productService.saveOrUpdate(product);
		return "redirect:/product/" + savedProduct.getId();
	}

	@RequestMapping(value = "/product/delete/{id}")
	public String deleteProduct(@PathVariable Integer id) {
		productService.delete(id);
		return "redirect:/products";
	}

}
