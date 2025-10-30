package com.ecobazaar.ecobazaar.controller;


import java.util.List;

/*import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecobazaar.ecobazaar.dto.UserReport;
import com.ecobazaar.ecobazaar.model.User;
import com.ecobazaar.ecobazaar.repository.UserRepository;
import com.ecobazaar.ecobazaar.service.UserReportService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final UserReportService userReportService;
    private final UserRepository userRepository;

    public ProductController(UserReportService userReportService, UserRepository userRepository) {
        this.userReportService = userReportService;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public UserReport getUserReport() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User currentUser = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return userReportService.getUserReport(currentUser.getId());
    }
}*/

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecobazaar.ecobazaar.model.Product;
import com.ecobazaar.ecobazaar.model.User;
import com.ecobazaar.ecobazaar.repository.UserRepository;
import com.ecobazaar.ecobazaar.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	private final ProductService productService;
	private final UserRepository userRepository;
	
	//constructor injection
	public ProductController(ProductService productService, UserRepository userRepository) {
		this.productService=productService;
		this.userRepository = userRepository;
	}
	
	@PreAuthorize("hasAnyRole('SELLER','ADMIN')")
	@PostMapping
	public Product addProduct(@RequestBody Product product) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User seller = userRepository.findByEmail(email)
		.orElseThrow(() -> new RuntimeException("Seller not found"));
		product.setSellerId(seller.getId());
		return productService.addProduct(product);
	}
	
	@GetMapping
	public List<Product> getAllProducts(){
		return productService.getAllProducts();
	}
	
	@PreAuthorize("hasAnyRole('SELLER','ADMIN')")
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
		return productService.updateProduct(id, product);
	}
	
	@PreAuthorize("hasAnyRole('SELLER','ADMIN')")
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
	}
	
	@GetMapping("/eco")
	public List<Product> getEcoCertified(){
		return productService.getEcoCertifiedProducts();
	}
	
	@GetMapping("/eco/sorted")
	public List<Product> getEcoCertifiedSorted(){
		return productService.getEcoCertifiedSortedByCarbonImpact();
	}
	

}
