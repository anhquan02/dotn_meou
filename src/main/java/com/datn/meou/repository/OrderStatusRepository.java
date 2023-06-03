package com.datn.meou.repository;

import com.datn.meou.entity.OrderStatus;
import com.datn.meou.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
}
