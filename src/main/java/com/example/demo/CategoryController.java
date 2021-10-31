package com.example.demo;

import java.util.List;

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
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	//http://localhost:8081/api/category/
	@GetMapping("/category")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<List<Category>> getCategory() throws ResourceNotFound {
		List<Category> categories = (List<Category>) categoryRepository.findAll();
		return ResponseEntity.ok().body(categories);
	}

	//http://localhost:8081/api/category/create?name=ram
	@GetMapping("/category/create")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Category> createCategory(
			@Valid Category category,
			@RequestParam(value="name")String name) throws ResourceNotFound {
		category.setCategory_id(category.getCategory_id());
		category.setName(name);
		categoryRepository.save(category);
		return ResponseEntity.ok().body(category);
	}
	
	//http://localhost:8081/api/category/update?id=4&name=TestUpdate
	@GetMapping("/category/update")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Category> updateCategory(
			@RequestParam(value="id")Integer category_id,
			@RequestParam(value="name")String name) throws ResourceNotFound {
		Category category = categoryRepository.findById(category_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		category.setName(name);
		categoryRepository.save(category);
		return ResponseEntity.ok().body(category);
	}
	
	//http://localhost:8081/api/category/delete?id=4
    @GetMapping("/category/delete")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<Category> deleteCategory(
    		@RequestParam(value="id")Integer categoy_id) throws ResourceNotFound{
		Category category = categoryRepository.findById(categoy_id)
				.orElseThrow(() -> new ResourceNotFound("Error not found"));
		
        categoryRepository.delete(category);
        return ResponseEntity.ok().body(category);
    }
}