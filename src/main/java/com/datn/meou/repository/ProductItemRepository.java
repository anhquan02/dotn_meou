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
    Optional<ProductItem> findByIdAndDeleted(Long id, Boolean status);

    Optional<ProductItem> findByIdAndStatus(Long id, Boolean status);
    Page<ProductItem> findAllByDeleted(Boolean status, Pageable pageable);

    List<ProductItem> findAllByDeleted(Boolean status);

    Page<ProductItem> findByDeletedAndNameContaining(Boolean status, String name, Pageable pageable);

    List<ProductItem> findAllByProductIdAndDeleted(Long id, Boolean status);

    ProductItem findByProductIdAndColorIdAndInsoleIdAndSizeIdAndSoleIdAndBrandId(Long productId, Long colorId, Long insoleId, Long sizeId, Long soleId, Long brandId);
    ProductItem findByProductId(Long id);
}
