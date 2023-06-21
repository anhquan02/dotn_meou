package com.datn.meou.repository;

import com.datn.meou.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    Page<OrderStatus> findAllByDeleted(Boolean deleted, Pageable pageable);

    Optional<OrderStatus> findByIdAndDeleted(Long id, Boolean deleted);
}
