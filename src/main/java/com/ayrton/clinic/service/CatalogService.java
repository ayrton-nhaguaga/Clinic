package com.ayrton.clinic.service;

import com.ayrton.clinic.dto.CatalogDTO;
import com.ayrton.clinic.model.Catalog;
import com.ayrton.clinic.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    public Catalog createCatalog(CatalogDTO dto){
        Catalog catalog = new Catalog();
        catalog.setName(dto.getName());
        catalog.setDescription(dto.getDescription());
        catalog.setPrice(dto.getPrice());
        catalog.setDurationMinutes(dto.getDurationMinutes());
        return catalogRepository.save(catalog);
    }

    public List<Catalog> getAllCatalogs(){
        return catalogRepository.findAll();
    }

    public Optional<Catalog> getById(String id){
        return catalogRepository.findById(id);
    }

    public List<Catalog> getByNameIgnoreCase(String name){
        return catalogRepository.findByNameIgnoreCase(name);
    }

    public List<Catalog> getByPrice(double price){
        return catalogRepository.findByPrice(price);
    }

    public List<Catalog> getByDurationMinutes(int durationMinutes){
        return catalogRepository.findByDurationMinutes(durationMinutes);
    }

    public Optional<Catalog> updateCatalogByName(String id, CatalogDTO dto){
        return catalogRepository.findById(id)
                .map(catalog -> {
                    catalog.setName(dto.getName());
                    catalog.setDescription(dto.getDescription());
                    catalog.setPrice(dto.getPrice());
                    catalog.setDurationMinutes(dto.getDurationMinutes());
                    return catalogRepository.save(catalog);
                });
    }

    public boolean deleteCatalog(String id){
        return catalogRepository.findById(id)
                .map(catalog -> {
                    catalogRepository.delete(catalog);
                    return true;
                })
                .orElse(false);
    }
}
