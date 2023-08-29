package com.datn.meou.repository;

import com.datn.meou.entity.Color;
import com.datn.meou.entity.ProductItem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long>, ProductItemRepositoryCustom {

    Optional<ProductItem> findByIdAndStatusGreaterThan(Long id, Integer status);

    Page<ProductItem> findAllByStatusGreaterThan(Integer status, Pageable pageable);

    List<ProductItem> findAllByStatusGreaterThan(Integer status);

    Page<ProductItem> findByStatusGreaterThanAndNameContaining(Integer status, String name, Pageable pageable);

    List<ProductItem> findAllByProductIdAndStatusGreaterThan(Long id, Integer status);

    ProductItem findByProductIdAndColorIdAndInsoleIdAndSizeIdAndSoleId(Long productId, Long colorId, Long insoleId, Long sizeId, Long soleId);


    @Query("SELECT pi FROM ProductItem pi WHERE pi.soleId = :soleId " +
            "AND pi.sizeId = :sizeId AND pi.productId = :productId AND pi.insoleId = :insoleId " +
            "AND pi.colorId = :colorId AND pi.status = 1")
    Optional<ProductItem> chooseForOnline(@Param("soleId") Long soleId,
                                          @Param("sizeId") Long sizeId,
                                          @Param("productId") Long productId,
                                          @Param("insoleId") Long insoleId,
                                          @Param("colorId") Long colorId);
}
