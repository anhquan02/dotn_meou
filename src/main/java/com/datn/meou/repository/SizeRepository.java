package com.datn.meou.repository;

import com.datn.meou.entity.Size;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Size findByName(String name);

    List<Size> findByNameContaining(String name);
}
