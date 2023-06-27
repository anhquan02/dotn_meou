package com.datn.meou.repository;

import com.datn.meou.entity.Color;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    Color findByName(String name);

    List<Color> findByNameContaining(String name);
}
