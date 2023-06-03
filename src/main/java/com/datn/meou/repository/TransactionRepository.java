package com.datn.meou.repository;

import com.datn.meou.entity.Product;
import com.datn.meou.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
