package com.datn.meou.repository;

import com.datn.meou.entity.ExchangeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeItemRepository extends JpaRepository<ExchangeItem, Long> {
    ExchangeItem findByIdAndDeleted(Long id, Boolean deleted);

//    List<ExchangeItem> findAllExchangeItemIdByCustomerIdAndDeleted(Integer ExchangeId, Boolean delete);
}
