package com.datn.meou.repository;

import com.datn.meou.entity.Insole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsoleRepository extends JpaRepository<Insole, Long> {
    Insole findByName(String name);
}
