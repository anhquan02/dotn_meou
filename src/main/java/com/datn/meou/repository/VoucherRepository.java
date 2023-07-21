package com.datn.meou.repository;

import com.datn.meou.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Page<Voucher> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
