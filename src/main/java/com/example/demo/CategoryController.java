package com.example.demo;

import java.util.List;
import java.util.Optional;

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

	//http://localhost:8081/api/category/create
	@PostMapping("/category/create")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<?> createCategory(
			@Valid @RequestBody Category categoryRequest){
		if (categoryRepository.existsByName(categoryRequest.getName())) {
			return ResponseEntity
					.badRequest().body(new MessageResponse("Error: Name is already taken"));
		}
		
		//Create new Category
		Category category = new Category(categoryRequest.getName());
		categoryRepository.save(category);
		return ResponseEntity.ok().body(category);
	}
	
	//http://localhost:8081/api/category/update?id=1
	@PutMapping("/category/update")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Category> updateCategory(@RequestParam(value="id")Integer id, @RequestBody Category categoryRequest) throws ResourceNotFound {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent()) {
			Category _category = category.get();
			_category.setName(categoryRequest.getName());
			
			return new ResponseEntity<>(categoryRepository.save(_category), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//http://localhost:8081/api/category/delete?name=ขนมปัง
    @DeleteMapping("/category/delete")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<Category> deleteCategory(
    		@RequestParam(value="name")String name) throws ResourceNotFound{
    	Category category = categoryRepository.findByName(name);
    	categoryRepository.delete(category);
    	return ResponseEntity.ok().body(category);
    }
}