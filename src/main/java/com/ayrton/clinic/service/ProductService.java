package com.ayrton.clinic.service;

import com.ayrton.clinic.dto.ProductDTO;
import com.ayrton.clinic.model.Product;
import com.ayrton.clinic.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(ProductDTO dto){
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setQuantity(dto.getQuantity());
        product.setUnit(dto.getUnit());
        product.setLastRestockDate(LocalDateTime.now());
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getById(String id){
        return productRepository.findById(id);
    }

    public List<Product> getByNameIgnoreCase(String name){
        return productRepository.findByNameIgnoreCase(name);
    }

    public List<Product> getByQuantity(int quantity){
        return productRepository.findByQuantity(quantity);
    }

    public List<Product> getByLastRestockDate(LocalDateTime lastRestockDate){
        return productRepository.findByLastRestockDate(lastRestockDate);
    }

    public Optional<Product> updateProduct(String id, ProductDTO dto){
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(dto.getName());
                    product.setDescription(dto.getDescription());
                    product.setQuantity(dto.getQuantity());
                    product.setUnit(dto.getUnit());
                    return productRepository.save(product);
                });
    }

    public boolean deleteProductByName(String id){
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return true;
                })
                .orElse(false);
    }
}
