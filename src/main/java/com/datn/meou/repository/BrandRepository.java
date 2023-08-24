package com.datn.meou.repository;

import com.datn.meou.entity.Brand;

import java.util.List;
import java.util.Optional;

import com.datn.meou.entity.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByIdAndStatus(Long id, Boolean status);

    Page<Brand> findAllByStatus(Boolean status, Pageable pageable);

    List<Brand> findAllByStatus(Boolean status);

    Page<Brand> findByStatusAndNameContaining(Boolean status, String name, Pageable pageable);

    @Query("SELECT s FROM Brand s,ProductItem pi " +
            "WHERE s.id = pi.soleId AND s.status = true AND pi.status = 1 AND pi.productId = :productId " +
            "GROUP BY s.id ORDER BY s.name")
    List<Brand> getAllBrandByProductId(@Param("productId") Long productId);
}
