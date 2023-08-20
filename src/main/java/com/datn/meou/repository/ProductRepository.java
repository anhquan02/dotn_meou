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

    Optional<Product> findByIdAndStatusGreaterThan(Long id, Integer status);
    Page<Product> findAllByStatusGreaterThan(Integer status, Pageable pageable);
    List<Product> findAllByStatusGreaterThan(Integer status);
    Page<Product> findByStatusGreaterThanAndNameContaining(Integer status, String name, Pageable pageable);
    Product findProductByName(String name);
}
