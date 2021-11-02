package com.example.demo;

import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.payload.response.MessageResponse;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	//http://localhost:8081/api/product/
	@GetMapping("/product")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<List<Product>> getProduct() throws ResourceNotFound {
		List<Product> products = (List<Product>) productRepository.findAll();
		return ResponseEntity.ok().body(products);
	}

	@GetMapping("/product-amount")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Integer> getProductAmount() throws ResourceNotFound {
		return ResponseEntity.ok().body(productRepository.getProductAmount());
	}

	@GetMapping("/product-amount-by-category")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<List<Object[]>> getProductAmountByCategory() throws ResourceNotFound {
		return ResponseEntity.ok().body(productRepository.getProductAmountByCategory());
	}
	
	@PostMapping("/product/create")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<?> createProduct(
			@Valid @RequestBody Product productRequest){
		if (productRepository.existsByName(productRequest.getName())) {
			return ResponseEntity
					.badRequest().body(new MessageResponse("Error: Name is already taken"));
		}
		
		//Create new Product
		/*{
    		"name": "Telephone",
    		"description": "เครื่องมือสื่อสารทา่งไกล",
    		"quantity": "29",
    		"category": {"category_id":"2"}
		}*/
		Product product = new Product();
		if (productRequest.getName() != null){
			product.setName(productRequest.getName());
		}
		else{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		if (productRequest.getDescription() != null){
			product.setDescription(productRequest.getDescription());
		}
		if (productRequest.getQuantity() != null){
			product.setQuantity(productRequest.getQuantity());
		}
		if (productRequest.getCategory() != null){
			Optional<Category> category = categoryRepository.findById(productRequest.getCategory().getCategory_id());
			product.setCategory(category.get());
		}
			
		productRepository.save(product);
		return ResponseEntity.ok().body(product);
	}
	
	//http://localhost:8081/api/product/update?id=1
	@PutMapping("/product/update")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Product> updateProduct(@RequestParam(value="id")Integer id, @RequestBody Product productRequest) throws ResourceNotFound {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			Product _product = product.get();
			if (productRequest.getName() != null) {
				_product.setName(productRequest.getName());
			}
			if (productRequest.getDescription() != null) {
				_product.setDescription(productRequest.getDescription());
			}
			if (productRequest.getQuantity() != null) {
				_product.setQuantity(productRequest.getQuantity());
			}
			if (productRequest.getCategory() != null) {
				Optional<Category> category = categoryRepository.findById(productRequest.getCategory().getCategory_id());
				_product.setCategory(category.get());
			}
			return new ResponseEntity<>(productRepository.save(_product), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//http://localhost:8081/api/product/delete?id=1
    @DeleteMapping("/product/delete")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(
    		@RequestParam(value="id")Integer id) throws ResourceNotFound{
		Optional<Product> product = productRepository.findById(id);	
		if(product.isPresent()){
			productRepository.delete(product.get());
			return ResponseEntity.ok().body("Product delete successful");
		}
		else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    	
    }
}
