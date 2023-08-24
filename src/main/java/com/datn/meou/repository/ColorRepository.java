package com.datn.meou.repository;

import com.datn.meou.entity.Color;

import java.util.List;
import java.util.Optional;

import com.datn.meou.entity.Sole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    Optional<Color> findByIdAndStatus(Long id, Boolean status);

    Page<Color> findAllByStatus(Boolean status, Pageable pageable);

    List<Color> findAllByStatus(Boolean status);

    Page<Color> findByStatusAndNameContaining(Boolean status, String name, Pageable pageable);

    @Query("SELECT s FROM Color s,ProductItem pi " +
            "WHERE s.id = pi.soleId AND s.status = true AND pi.status = 1 AND pi.productId = :productId " +
            "GROUP BY s.id ORDER BY s.name")
    List<Color> getAllColorByProductId(@Param("productId") Long productId);
}
