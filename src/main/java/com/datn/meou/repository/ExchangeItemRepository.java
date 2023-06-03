package com.datn.meou.repository;

import com.datn.meou.entity.ExchangeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeItemRepository extends JpaRepository<ExchangeItem, Long> {
}
