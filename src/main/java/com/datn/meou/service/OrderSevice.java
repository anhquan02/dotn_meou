package com.datn.meou.service;


import com.datn.meou.entity.Orders;
import com.datn.meou.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderSevice {

    private final OrdersRepository ordersRepository;

    public Page<Orders> findAll(Pageable pageable) {
        return this.ordersRepository.findAllByDeleted(true, pageable);
    }
}
