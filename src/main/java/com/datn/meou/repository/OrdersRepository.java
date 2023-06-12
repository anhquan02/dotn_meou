package com.datn.meou.repository;

import com.datn.meou.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Page<Orders> findAllByDeleted(Boolean deleted, Pageable pageable);
}
