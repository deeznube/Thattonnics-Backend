package com.example.demo;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	//http://localhost:8082/api/product/create?name=Laptop&desc=โน๊ตบุ๊คเท่ๆ&qty=22&category_id=1
	@GetMapping("/product/create")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Product> createProduct(
			@Valid Product product,
			@RequestParam(value="name")String name,
			@RequestParam(value="desc")String desc,
			@RequestParam(value="qty")Integer qty,
			@RequestParam(value="category_id")Integer category_id) throws ResourceNotFound {
		Category category = categoryRepository.findById(category_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		product.setProduct_id(product.getProduct_id());
		product.setName(name);
		product.setDescription(desc);
		product.setQuantity(qty);
		product.setCategory(category);
		productRepository.save(product);
		return ResponseEntity.ok().body(product);
	}
}
