package com.datn.meou.repository;

import com.datn.meou.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderRepositoryCustom {

    List<OrderItem> findAllByOrderId(Long orderId);

    List<OrderItem> findAllByProductItemId(Long productItemId);
}
