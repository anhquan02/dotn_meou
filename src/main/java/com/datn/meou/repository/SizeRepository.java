package com.datn.meou.repository;

import com.datn.meou.entity.Brand;
import com.datn.meou.entity.Size;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findByIdAndStatus(Long id, Boolean status);

    List<Size> findAllByName(String name);

    Page<Size> findAllByStatus(Boolean status, Pageable pageable);

    List<Size> findAllByStatus(Boolean status);

    Page<Size> findByStatusAndNameContaining(Boolean status, String name, Pageable pageable);

    @Query("SELECT s FROM Size s,ProductItem pi " +
            "WHERE s.id = pi.sizeId AND s.status = true AND pi.status = 1 AND pi.productId = :productId " +
            "GROUP BY s.id ORDER BY s.name")
    List<Size> getAllSizeByProductId(@Param("productId") Long productId);
}
