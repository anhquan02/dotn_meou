package com.datn.meou.repository;

import com.datn.meou.entity.Insole;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsoleRepository extends JpaRepository<Insole, Long> {
    Insole findByName(String name);

    List<Insole> findByNameContaining(String name);
}
