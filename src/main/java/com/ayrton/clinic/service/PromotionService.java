package com.ayrton.clinic.service;

import com.ayrton.clinic.dto.PromotionDTO;
import com.ayrton.clinic.model.Promotion;
import com.ayrton.clinic.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    public Promotion createPromotion(PromotionDTO dto){
        Promotion promotion = new Promotion();
        promotion.setId(dto.getId());
        promotion.setTitle(dto.getTitle());
        promotion.setDescription(dto.getDescription());
        promotion.setDiscountPercent(dto.getDiscountPercent());
        promotion.setCode(dto.getCode());
        promotion.setValidFrom(dto.getValidFrom());
        promotion.setValidTo(dto.getValidTo());
        promotion.setActive(dto.isActive());
        return  promotionRepository.save(promotion);
    }

    public List<Promotion> getAllPromotions(){
        return promotionRepository.findAll();
    }

    public Optional<Promotion> getById(String id){
        return promotionRepository.findById(id);
    }

    public List<Promotion> getByTitleIgnoreCase(String title){
        return promotionRepository.findByTitleIgnoreCase(title);
    }

    public List<Promotion> getByDiscountPercent(double discountPercent){
        return promotionRepository.findByDiscountPercent(discountPercent);
    }

    public List<Promotion> getByCodeIgnoreCase(String code){
        return promotionRepository.findByCodeIgnoreCase(code);
    }

    public List<Promotion> getByValidFrom(LocalDateTime validFrom){
        return promotionRepository.findByValidFrom(validFrom);
    }

    public List<Promotion> getByValidTo(LocalDateTime validTo){
        return promotionRepository.findByValidTo(validTo);
    }

    public List<Promotion> getByActive(boolean active){
        return promotionRepository.findByActive(active);
    }

    public boolean applyPromotion(String code) {
        List<Promotion> promotions = promotionRepository.findByCodeIgnoreCase(code);

        for (Promotion promotion : promotions) {
            if (promotion.isActive() && isValidNow(promotion)) {
                promotion.setActive(false);
                promotionRepository.save(promotion);
                return true;
            }
        }

        return false;
    }

    private boolean isValidNow(Promotion promotion) {
        LocalDateTime now = LocalDateTime.now();
        return (promotion.getValidFrom().isBefore(now) || promotion.getValidFrom().isEqual(now)) &&
                (promotion.getValidTo().isAfter(now) || promotion.getValidTo().isEqual(now));
    }



    public List<Promotion> updatePromotion(String title, PromotionDTO dto){
        List<Promotion> exits = promotionRepository.findByTitleIgnoreCase(title);

        for (Promotion p : exits){
            p.setTitle(dto.getTitle());
            p.setDescription(dto.getDescription());
            p.setDiscountPercent(dto.getDiscountPercent());
            p.setCode(dto.getCode());
            p.setValidTo(dto.getValidTo());
            p.setActive(dto.isActive());
            promotionRepository.save(p);
        }
        return  exits;
    }

    public boolean deletePromotion(String title){
        List<Promotion> promotions = promotionRepository.findByTitleIgnoreCase(title);

        if (!promotions.isEmpty()){
            promotionRepository.deleteAll(promotions);
            return true;
        }
        return false;
    }
}
