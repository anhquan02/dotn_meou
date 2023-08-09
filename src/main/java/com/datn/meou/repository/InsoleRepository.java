package com.datn.meou.repository;

import com.datn.meou.entity.Insole;

import java.util.List;
import java.util.Optional;

import com.datn.meou.entity.Sole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsoleRepository extends JpaRepository<Insole, Long> {
    Optional<Insole> findByIdAndStatus(Long id, Boolean status);

    Page<Insole> findAllByStatus(Boolean status, Pageable pageable);

    List<Insole> findAllByStatus(Boolean status);

    Page<Insole> findByStatusAndNameContaining(Boolean status, String name, Pageable pageable);
}
