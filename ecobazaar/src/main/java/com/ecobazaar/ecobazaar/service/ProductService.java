package com.ecobazaar.ecobazaar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecobazaar.ecobazaar.model.Product;
import com.ecobazaar.ecobazaar.repository.ProductRepository;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;
	
	//constructor injection
	public ProductService(ProductRepository productRepository) {
		this.productRepository=productRepository;
	}
	
	//create
	public Product addProduct(Product product) {
		product.setEcoCertified(false);
		return productRepository.save(product);
	}
	
	//read
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	//update
	public Product updateProduct(Long id,Product product) {
		return productRepository.findById(id)
				.map(updatedProduct->{
					updatedProduct.setName(product.getName());
					updatedProduct.setDetails(product.getDetails());
					updatedProduct.setPrice(product.getPrice());
					updatedProduct.setCarbonImpact(product.getCarbonImpact());
					updatedProduct.setSellerId(product.getSellerId());
					return productRepository.save(updatedProduct);
				})
				.orElseThrow(()->new RuntimeException("Product not found"));
	}
	
	//delete
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
	
	//filter
	public List<Product> getEcoCertifiedProducts(){
		return productRepository.findByEcoCertifiedTrue();
	}
	
	//filterByCarbonImpact
	public List<Product> getEcoCertifiedSortedByCarbonImpact(){
		return productRepository.findByEcoCertifiedTrueOrderByCarbonImpactAsc();
	}
}
