package com.datn.meou.repository;

import com.datn.meou.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Page<Voucher> findAllByStatusAndNameContaining(Boolean status, Pageable pageable, String name);

    Page<Voucher> findAllByStatus(Boolean status, Pageable pageable);

    Optional<Voucher> findByIdAndStatus(Long id, Boolean status);

    List<Voucher> findByStatus(Boolean status);

    List<Voucher> findByStatusAndNameContaining(Boolean status, String name);

}
