package com.practice.springbootjpa.producthive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.practice.springbootjpa.producthive.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
