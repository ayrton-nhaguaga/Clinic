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
        product.setId(dto.getId());
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

    public List<Product> updateProduct(String name, ProductDTO dto){
        List<Product> exists = productRepository.findByNameIgnoreCase(name);

        for (Product p : exists){
            p.setName(dto.getName());
            p.setDescription(dto.getDescription());
            p.setQuantity(dto.getQuantity());
            p.setUnit(dto.getUnit());
            productRepository.save(p);
        }
        return exists;
    }

    public boolean deleteProductByName(String name){
        List<Product> products = productRepository.findByNameIgnoreCase(name);

        if (!products.isEmpty()){
            productRepository.deleteAll(products);
            return true;
        }
        return false;
    }
}
