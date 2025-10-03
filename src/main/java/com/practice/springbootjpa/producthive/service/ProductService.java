package com.practice.springbootjpa.producthive.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.practice.springbootjpa.producthive.model.Product;
import com.practice.springbootjpa.producthive.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository prepo;

	public ProductService(ProductRepository prepo) {
		super();
		this.prepo = prepo;
	}


	public Product saveProduct(Product p) {
		return prepo.save(p);     //INVOKES PRE DEFINED METHOD save() OF JPA REPOSITORY
	}


	public List<Product> listAll(){
		return prepo.findAll();   //INVOKES PRE DEFINED METHOD findAll() OF JPA REPOSITORY
	}


	// Optional return type is to handle Null Pointer Exception
	public Optional<Product> getSingleProduct(Long pid){
		return prepo.findById(pid);    //Invokes pre-defined method findById() of JPA repository
	}

	public void deleteProduct(Long pid) {
		prepo.deleteById(pid);       //Invokes pre-defined method deleteById() of JPA repository
	}

}
