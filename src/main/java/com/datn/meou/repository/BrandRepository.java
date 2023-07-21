package com.datn.meou.repository;

import com.datn.meou.entity.Brand;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByName(String name);
    
    List<Brand> findByNameContaining(String name);
}
