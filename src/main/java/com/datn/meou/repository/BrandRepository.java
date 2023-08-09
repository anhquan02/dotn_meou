package com.datn.meou.repository;

import com.datn.meou.entity.Brand;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByIdAndStatus(Long id, Boolean status);

    Page<Brand> findAllByStatus(Boolean status, Pageable pageable);

    List<Brand> findAllByStatus(Boolean status);

    Page<Brand> findByStatusAndNameContaining(Boolean status, String name, Pageable pageable);
}
