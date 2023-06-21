package com.datn.meou.repository;

import com.datn.meou.model.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionRepositoryCustom {

    Page<TransactionDTO> findByNameOrders(String name, Pageable pageable);
}
