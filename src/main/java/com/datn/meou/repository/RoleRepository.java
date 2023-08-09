package com.datn.meou.repository;

import com.datn.meou.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByIdAndStatus(Long id, Boolean status);
}
