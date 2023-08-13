package com.datn.meou.repository;

import com.datn.meou.entity.Transaction;
import com.datn.meou.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionStatusRepository extends JpaRepository<TransactionStatus, Long> {

    List<TransactionStatus> findByOrderId(Long orderId);
}
