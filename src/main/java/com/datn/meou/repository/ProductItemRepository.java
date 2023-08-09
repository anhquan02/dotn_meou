package com.datn.meou.repository;

import com.datn.meou.entity.ProductItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long>, ProductItemRepositoryCustom {
    List<ProductItem> findByNameContaining(String name);

    @Query(value = "SELECT * FROM dotn_product_item WHERE name LIKE %?1% AND brand_id LIKE %?2% AND sole_id LIKE %?3% AND insole_id LIKE %?4% AND size_id LIKE %?5% AND color_id LIKE %?6% AND status LIKE %?7%", nativeQuery = true)
    List<ProductItem> findFilter(String name, String brandId, String soleId, String insoleId, String sizeId, String colorId, String status);
}
