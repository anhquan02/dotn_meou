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
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    Optional<Product> findByIdAndDeleted(Long id, Boolean status);
    Optional<Product> findByIdAndStatus(Long id, Boolean status);

    Page<Product> findAllByDeleted(Boolean status, Pageable pageable);

    List<Product> findAllByDeleted(Boolean status);

    Page<Product> findByDeletedAndNameContaining(Boolean status, String name, Pageable pageable);

    Product findProductByName(String name);
}
