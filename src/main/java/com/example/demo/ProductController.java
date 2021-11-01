package com.example.demo;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.request.CategoryRequest;
import com.example.demo.payload.request.ProductRequest;
import com.example.demo.payload.response.MessageResponse;

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
	
	@PostMapping("/product/create")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<?> createProduct(
			@Valid @RequestBody Product productRe){
		if (productRepository.existsByName(productRe.getName())) {
			return ResponseEntity
					.badRequest().body(new MessageResponse("Error: Name is already taken"));
		}
		
		//Create new Product
		//Optional<Category> category = categoryRepository.findById(productRe.getCategory());
		System.out.println(productRe.getCategory()); //getCategory can get NULL !!!--------------------
		Product product = new Product(
				productRe.getProduct_id(),
				productRe.getName(),
				productRe.getDescription(),
				productRe.getQuantity(),
				productRe.getCategory());
		//product.setCategory(categoryRepository.getById(productRe.setCategory(productRe.getCategory())));
		System.out.println(product);
		productRepository.save(product);
		return ResponseEntity.ok().body(product);
	}
	
	//http://localhost:8081/api/product/delete?name=Notebook
    @DeleteMapping("/product/delete")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<Product> deleteProduct(
    		@RequestParam(value="name")String name) throws ResourceNotFound{
    	Product product = productRepository.findByName(name);		
    	productRepository.delete(product);
    	return ResponseEntity.ok().body(product);
    }
}
