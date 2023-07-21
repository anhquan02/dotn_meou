package com.datn.meou.repository;

import com.datn.meou.model.ExchangeItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeItemRepositoryCustom  {
    List<ExchangeItemDTO> findAllExchangeItem(String name);
}
