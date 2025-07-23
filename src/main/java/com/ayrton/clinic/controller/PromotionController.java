package com.ayrton.clinic.controller;

import com.ayrton.clinic.dto.PromotionDTO;
import com.ayrton.clinic.model.Promotion;
import com.ayrton.clinic.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@RequestBody PromotionDTO dto){
        Promotion promotion = promotionService.createPromotion(dto);
        return new ResponseEntity<>(promotion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Promotion>> getAllPromotions(){
        List<Promotion> promotions = promotionService.getAllPromotions();
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Promotion>> getById(@RequestParam String id){
        Optional<Promotion> promotion = promotionService.getById(id);
        return new ResponseEntity<>(promotion, HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Promotion>> getByTitleIgnoreCase(@RequestParam String title){
        List<Promotion> promotions = promotionService.getByTitleIgnoreCase(title);
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @GetMapping("/dicount-percent/{discountPercent}")
    public ResponseEntity<List<Promotion>> getByDiscountPercent(@RequestParam double discountPercent){
        List<Promotion> promotions = promotionService.getByDiscountPercent(discountPercent);
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<List<Promotion>> getByCodeIgnoreCase(@RequestParam String code){
        List<Promotion> promotions = promotionService.getByCodeIgnoreCase(code);
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @GetMapping("/valid-from/{validFrom")
    public ResponseEntity<List<Promotion>> getByValidFrom(@RequestParam LocalDateTime validFrom){
        List<Promotion> promotions = promotionService.getByValidFrom(validFrom);
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @GetMapping("/valid-to/{validTo}")
    public ResponseEntity<List<Promotion>> getByValidTo(@RequestParam LocalDateTime validTo){
        List<Promotion> promotions = promotionService.getByValidTo(validTo);
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @GetMapping("/active/{active}")
    public ResponseEntity<List<Promotion>> getByActive(@RequestParam boolean active){
        List<Promotion> promotions = promotionService.getByActive(active);
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @PutMapping("/title/{title}")
    public ResponseEntity<List<Promotion>> updatePromotion(@RequestParam String title, @RequestBody PromotionDTO dto){
        List<Promotion> updatedList = promotionService.updatePromotion(title, dto);

        if (updatedList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
        return ResponseEntity.ok(updatedList);
    }

    @DeleteMapping("/title/{title}")
    public ResponseEntity<Void> deletePromotion(@RequestParam String title){
        if (promotionService.deletePromotion(title)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
