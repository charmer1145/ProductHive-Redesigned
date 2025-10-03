package com.practice.springbootjpa.producthive.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.springbootjpa.producthive.exception.ResourceNotFoundException;
import com.practice.springbootjpa.producthive.model.Product;
import com.practice.springbootjpa.producthive.service.ProductService;


//@RestController
//@RequestMapping(value = "/api")   // base url for this controller endpoints
//Open PostMan, make a POST Request - http://localhost:8088/producthive/api/products
//Select body -> raw -> JSON 
//Insert JSON product object.

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService pservice;

	@PostMapping("/products")
	public ResponseEntity<Product> saveProduct(@Validated @RequestBody Product product){
		//TODO: process POST request
		try {
			Product p=pservice.saveProduct(product);
			return ResponseEntity.status(HttpStatus.CREATED).body(p);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}


	//Open PostMan/Browser, make a GET Request - http://localhost:8088/producthive/api/products
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		try {
			List<Product> products=pservice.listAll();//Invoke listAll() service method
			return ResponseEntity.ok(products);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}



	//Open PostMan/Browser, make a GET Request - http://localhost:8088/product-hive/api/products/1004
	//Exception handling is done with Custom Exceptions
	@GetMapping("/products/{pid}")
	public ResponseEntity<Product> getProductById(@PathVariable(value="pid") Long pId) 
			throws ResourceNotFoundException{

		Product p=pservice.getSingleProduct(pId).orElseThrow(() ->
		new  ResourceNotFoundException("Product Not Found for this Id : "+pId));

		return ResponseEntity.ok(p);
	}

	//Open PostMan, make a PUT Request - http://localhost:8088/product-hive/api/products/1004
	//Select body -> raw -> JSON 
	//Update JSON product object with new Values.
	/*
	 * Example JSON for PUT (all fields):
	 * {
		  			"name": "Laptop",
		  			"brand": "Dell",
		  			"price": 55000,
		  			"madein": "USA"
				}
	 */
	//@PutMapping → used for full updates (instead of partial updates with @PatchMapping).
	@PutMapping("/products/{pid}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value="pid") Long pId,
			@Validated @RequestBody Product p) throws ResourceNotFoundException {

		Product product=pservice.getSingleProduct(pId).
				orElseThrow(() -> new ResourceNotFoundException("Product Not Found for this Id :"+pId));
		//Update product with new values
		product.setBrand(p.getBrand());
		product.setMadein(p.getMadein());
		product.setName(p.getName());
		product.setPrice(p.getPrice());

		final Product updatedProduct=pservice.saveProduct(product); // invokes service layer method
		return ResponseEntity.ok().body(updatedProduct);
	}

	//Open PostMan, make a PATCH Request - http://localhost:8088/product-hive/api/products/1004
	//Select body -> raw -> JSON
	//Update JSON product object with new Values.
	/*
	 * Example JSON for PATCH (only price and madein):
	 * {
		  			"price": 45000,
		  			"madein": "Germany"
				}
	 */
	//@PatchMapping → used for partial updates (instead of full updates with @PutMapping).

	@PatchMapping("/products/{pid}")
	public ResponseEntity<Product> updateProductPriceAndMadein(
			@PathVariable(value = "pid") Long pId,
			@Validated @RequestBody Product p) throws ResourceNotFoundException {

		// Fetch existing product from DB
		Product product = pservice.getSingleProduct(pId)
				.orElseThrow(() -> new ResourceNotFoundException("Product Not Found for this Id: " + pId));

		// Update only price and madein if provided in request
		if (p.getPrice() != 0.0f) {
			product.setPrice(p.getPrice());
		}
		if (p.getMadein() != null) {
			product.setMadein(p.getMadein());
		}

		// Save updated product
		final Product updatedProduct = pservice.saveProduct(product);
		return ResponseEntity.ok().body(updatedProduct);
	}

	//Open PostMan, make a DELETE Request - http://localhost:8088/product-hive/api/products/1004
	@DeleteMapping("/products/{pid}")
	public ResponseEntity<Map<String,Boolean>> deleteProduct(@PathVariable(value="pid") Long pId) 
			throws ResourceNotFoundException {

		pservice.getSingleProduct(pId).  // invokes service layer method
		orElseThrow(() -> new ResourceNotFoundException("Product Not Found for this Id :"+pId));

		pservice.deleteProduct(pId); // invokes service layer method

		Map<String,Boolean> response=new HashMap<>(); //Map Stores Data in key-value pairs
		response.put("Deleted", Boolean.TRUE);

		return ResponseEntity.ok(response);
	}





}
