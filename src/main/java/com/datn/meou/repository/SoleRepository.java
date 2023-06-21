package com.datn.meou.repository;

import com.datn.meou.entity.Sole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoleRepository extends JpaRepository<Sole, Long> {
    Sole findByName(String name);
}
