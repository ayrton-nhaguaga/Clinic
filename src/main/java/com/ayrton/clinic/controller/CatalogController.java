package com.ayrton.clinic.controller;

import com.ayrton.clinic.dto.CatalogDTO;
import com.ayrton.clinic.model.Catalog;
import com.ayrton.clinic.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/catalogs")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @PostMapping
    public ResponseEntity<Catalog> createCatalog (@RequestBody CatalogDTO dto){
        Catalog catalog = catalogService.createCatalog(dto);
        return new ResponseEntity<>(catalog, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Catalog>> getAllCatalogs(){
        List<Catalog> catalogs = catalogService.getAllCatalogs();
        return new ResponseEntity<>(catalogs, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Catalog>> getById(@RequestParam String id){
        Optional<Catalog> catalog = catalogService.getById(id);
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Catalog>> getByNameIgnoreCase(@RequestParam String name){
        List<Catalog> catalogs = catalogService.getByNameIgnoreCase(name);
        return new ResponseEntity<>(catalogs, HttpStatus.OK);
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<Catalog>> getByPrice(@RequestParam double price){
        List<Catalog> catalogs = catalogService.getByPrice(price);
        return new ResponseEntity<>(catalogs, HttpStatus.OK);
    }

    @GetMapping("/duration-minutes/{durationMinutes}")
    public ResponseEntity<List<Catalog>> getByDurationMinutes(@RequestParam int durationMinutes){
        List<Catalog> catalogs = catalogService.getByDurationMinutes(durationMinutes);
        return new ResponseEntity<>(catalogs, HttpStatus.OK);
    }

    @PutMapping("/name/{name}")
    public ResponseEntity<List<Catalog>> updateCatalogByName(@RequestParam String name, @RequestBody CatalogDTO dto){
        List<Catalog> updatedList = catalogService.updateCatalogByName(name, dto);

        if (updatedList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedList);
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<Void> deleteCatalog(@RequestParam String name){
        if (catalogService.deleteCatalog(name)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
