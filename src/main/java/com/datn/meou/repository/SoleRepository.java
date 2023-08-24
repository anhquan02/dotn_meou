package com.datn.meou.repository;

import com.datn.meou.entity.Size;
import com.datn.meou.entity.Sole;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SoleRepository extends JpaRepository<Sole, Long> {
    Optional<Sole> findByIdAndStatus(Long id, Boolean status);

    Page<Sole> findAllByStatus(Boolean status, Pageable pageable);

    List<Sole> findAllByStatus(Boolean status);

    Page<Sole> findByStatusAndNameContaining(Boolean status, String name, Pageable pageable);

    @Query("SELECT s FROM Sole s,ProductItem pi " +
            "WHERE s.id = pi.soleId AND s.status = true AND pi.status = 1 AND pi.productId = :productId " +
            "GROUP BY s.id ORDER BY s.name")
    List<Sole> getAllSoleByProductId(@Param("productId") Long productId);
}
