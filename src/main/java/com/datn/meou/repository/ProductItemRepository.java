package com.datn.meou.repository;

import com.datn.meou.entity.Color;
import com.datn.meou.entity.ProductItem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long>, ProductItemRepositoryCustom {
    Optional<ProductItem> findByIdAndStatus(Long id, Boolean status);

    Page<ProductItem> findAllByStatus(Boolean status, Pageable pageable);

    List<ProductItem> findAllByStatus(Boolean status);

    Page<ProductItem> findByStatusAndNameContaining(Boolean status, String name, Pageable pageable);

    List<ProductItem> findAllByProductIdAndStatus(Long id, Boolean status);

    ProductItem findByProductIdAndColorIdAndInsoleIdAndSizeIdAndSoleId(Long productId, Long colorId, Long insoleId, Long sizeId, Long soleId);

    ProductItem findByProductId(Long id);
}
