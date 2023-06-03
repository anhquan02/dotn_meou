package com.datn.meou.repository;

import com.datn.meou.entity.Voucher_Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherOrderRepository extends JpaRepository<Voucher_Order, Long> {
}
