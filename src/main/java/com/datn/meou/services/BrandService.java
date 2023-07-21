package com.datn.meou.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    public Page<Brand> findAllBrands(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    public Brand findBrandByName(String name) {
        return brandRepository.findByName(name);
    }

    public Page<Brand> findByNameContaining(String name, Pageable pageable) {
        List<Brand> brands = brandRepository.findByNameContaining(name);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Brand> list;
        if (brands.size() < startItem) {
            return Page.empty();
        } else {
            int toIndex = Math.min(startItem + pageSize, brands.size());
            list = brands.subList(startItem, toIndex);
        }
        Page<Brand> brandPage = new PageImpl<Brand>(list, PageRequest.of(currentPage, pageSize), brands.size());
        return brandPage;
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
