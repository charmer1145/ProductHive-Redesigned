package com.practice.springbootjpa.producthive.controller;

import com.practice.springbootjpa.producthive.model.Product;
import com.practice.springbootjpa.producthive.service.ProductService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


//
//@Controller
//@RequestMapping("/web/products")
//public class ProductWebController {
//
//	private final ProductService productService;
//
//	public ProductWebController(ProductService productService) {
//		this.productService = productService;
//	}
//
//	// Simple test endpoint
//	@GetMapping("/test")
//	public String testPage(Model model) {
//		model.addAttribute("message", "Thymeleaf is working!");
//		return "test"; // This will look for test.html
//	}
//
//	// Home page with menu - SIMPLIFIED
//	@GetMapping("/home")
//	public String homePage(Model model) {
//		try {
//			List<Product> products = productService.listAll();
//			model.addAttribute("products", products);
//			return "home";
//		} catch (Exception e) {
//			// If home.html doesn't exist, redirect to test
//			return "redirect:/web/products/test";
//		}
//	}
//
//	// Rest of your methods...
//}






@Controller
@RequestMapping("/web/products")
public class ProductWebController {

    private final ProductService productService;

    public ProductWebController(ProductService productService) {
        this.productService = productService;
    }

    // Home page with menu
    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("products", productService.listAll());
        return "home";
    }

    // Show all products
    @GetMapping("/list")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.listAll());
        return "product-list";
    }

    // Show add product form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    // Process add product form
    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/web/products/list";
    }

    // Show edit product form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getSingleProduct(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        model.addAttribute("product", product);
        return "product-edit-form";
    }

    // Process edit product form
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        Product existingProduct = productService.getSingleProduct(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        existingProduct.setName(product.getName());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setMadein(product.getMadein());
        
        productService.saveProduct(existingProduct);
        return "redirect:/web/products/list";
    }

    // Delete product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/web/products/list";
    }
}