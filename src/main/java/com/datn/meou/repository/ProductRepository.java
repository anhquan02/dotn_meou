package com.datn.meou.repository;

import com.datn.meou.entity.Product;

import java.util.List;
import java.util.Optional;

import com.datn.meou.entity.Sole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndStatus(Long id, Boolean status);

    Page<Product> findAllByStatus(Boolean status, Pageable pageable);

    List<Product> findAllByStatus(Boolean status);

    Page<Product> findByStatusAndNameContaining(Boolean status, String name, Pageable pageable);

    Product findProductByName(String name);
}
