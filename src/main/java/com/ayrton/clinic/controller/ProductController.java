package com.ayrton.clinic.controller;

import com.ayrton.clinic.dto.ProductDTO;
import com.ayrton.clinic.model.Product;
import com.ayrton.clinic.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO dto){
        Product product = productService.createProduct(dto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Product>> getById (@RequestParam String id){
        Optional<Product> product = productService.getById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Product>> getByNameIgnoreCase(@RequestParam String name){
        List<Product> products = productService.getByNameIgnoreCase(name);
        return  new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/quantity/{quantity}")
    public ResponseEntity<List<Product>> getByQuantity(@RequestParam int quantity){
        List<Product> products = productService.getByQuantity(quantity);
        return  new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/last-restock-date/{lastRestockDate")
    public ResponseEntity<List<Product>> getByLastRestockDate(@RequestParam LocalDateTime lastRestockDate){
        List<Product> products = productService.getByLastRestockDate(lastRestockDate);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/name/{name}")
    public ResponseEntity<List<Product>> updateProduct(@RequestParam String name, @RequestBody ProductDTO dto){
        List<Product> updatedList = productService.updateProduct(name, dto);

        if (updatedList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedList);
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<Void> deleteProductByName(@RequestParam String name){
        if (productService.deleteProductByName(name)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
