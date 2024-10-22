package com.crud.springboot.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.springboot.models.ProductDto;
import com.crud.springboot.models.Products;
import com.crud.springboot.repository.ProductRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
	
	@Autowired
	private ProductRepository repo;
	
	@GetMapping
	public List<Products> getProducts() {
		return repo.findAll();
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Products> getProducts(@PathVariable int id) {
		Products products = repo.findById(id).orElse(null);
		
		if(products == null) {
			return ResponseEntity.notFound().build();
			
		}
			return ResponseEntity.ok(products);
	}
		
     @PostMapping
		public ResponseEntity<Object> createProducts(
			@Valid @RequestBody ProductDto productDto,
			BindingResult result
			)
		{
			double price = 0;
			try {
				price = Double.parseDouble(productDto.getPrice());
			}
			catch(Exception ex) {
				result.addError(new FieldError ("productDto" , "price ",
						"The price should be a number"));
			}
			
			if(result.hasErrors()) {
				var errorsList = result.getAllErrors();
				var errorsMap = new HashMap<String, String>();
			
				for(int i =0; i < errorsList.size();i++) {
					var error = (FieldError) errorsList.get(i);
					errorsMap.put(error.getField(), error.getDefaultMessage());
				}
				return ResponseEntity.badRequest().body(errorsMap);
			}
			
			Products products = new Products();
			
			products.setName(productDto.getName());
			products.setBrand(productDto.getBrand());
			products.setCategory(productDto.getCategory());
			products.setPrice(price);
			products.setDescription(productDto.getDescription());
			//products.setCreatedAt(new Date());
			
			repo.save(products);
			
			return ResponseEntity.ok(products);
		}
		
		@PutMapping("{id}")
		public ResponseEntity<Object> updateProducts(
				@PathVariable int id,
				@Valid @RequestBody ProductDto productDto,
				BindingResult result
				){
			Products products =repo.findById(id).orElse(null);
			if(products == null) {
				return ResponseEntity.notFound().build();			
				
			}
double price = 0;
		try {
			price = Double.parseDouble(productDto.getPrice());
		}
		catch(Exception ex) {
			result.addError(new FieldError ("productDto" , "price ", 
					"The price should be a number"));
		}
		
		if(result.hasErrors()) {
			var errorsList = result.getAllErrors();
			var errorsMap = new HashMap<String, String>();
		
			for(int i =0; i < errorsList.size(); i++) {
				var error = (FieldError) errorsList.get(i);
				errorsMap.put(error.getField(), error.getDefaultMessage());
		}
			return ResponseEntity.badRequest().body(errorsMap);
		}
				
		products.setName(productDto.getName());
		products.setBrand(productDto.getBrand());
		products.setCategory(productDto.getCategory());
		products.setPrice(price);
		products.setDescription(productDto.getDescription());
		//products.setCreatedAt(new Date());
		
		repo.save(products);
		
		return ResponseEntity.ok(products);
		}

@DeleteMapping("{id}")
public ResponseEntity<Object>deleteProducts(@PathVariable int id)
{
	Products products = repo.findById(id).orElse(null);
	if(products == null) {
		return ResponseEntity.notFound().build();
	}
	repo.delete(products);
	return ResponseEntity.ok().build();
}
}


