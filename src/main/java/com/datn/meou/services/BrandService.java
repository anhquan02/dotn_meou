package com.datn.meou.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.datn.meou.entity.Brand;
import com.datn.meou.repository.BrandRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public Brand saveBrand(String name) {
        Brand findBrandByName = this.findBrandByName(name);
        if (findBrandByName != null) {
            return findBrandByName;
        }
        Brand brand = Brand.builder().name(name).build();
        return brandRepository.save(brand);
    }

    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }
    
    public Brand findBrandByName(String name) {
        return brandRepository.findByName(name);
    }

    public Brand findById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }

    public void deleteBrand(Long id) {
        Brand brand = this.findById(id);
        if (brand != null) {
            brand.setDeleted(!brand.getDeleted());
            brandRepository.save(brand);
        }
    }
}
