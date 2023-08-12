package com.datn.meou.repository;

import com.datn.meou.model.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    Page<OrderDTO> findAll(OrderDTO dto, Pageable pageable);

    OrderDTO findByOrderId(Long idOrder);

}
