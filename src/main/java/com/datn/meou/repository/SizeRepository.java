package com.datn.meou.repository;

import com.datn.meou.entity.Brand;
import com.datn.meou.entity.Size;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findByIdAndStatus(Long id, Boolean status);

    Page<Size> findAllByStatus(Boolean status, Pageable pageable);

    List<Size> findAllByStatus(Boolean status);

    Page<Size> findByStatusAndNameContaining(Boolean status, String name, Pageable pageable);
}
