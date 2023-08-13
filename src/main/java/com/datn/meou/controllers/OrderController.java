package com.datn.meou.controllers;


import com.datn.meou.model.BrandDTO;
import com.datn.meou.model.ChangeStatus;
import com.datn.meou.model.CounterSaleDTO;
import com.datn.meou.model.OrderDTO;
import com.datn.meou.services.OrderSevice;
import com.datn.meou.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderSevice orderSevice;

    @PostMapping("all-page")
    private ResponseEntity<?> findAllPage(@RequestBody OrderDTO dto, Pageable pageable) {
        return ResponseUtil.ok(this.orderSevice.findAll(dto, pageable));
    }

    @PutMapping("change-status")
    private ResponseEntity<?> changeStatusByOrder(@RequestBody ChangeStatus dto) {
        return ResponseUtil.ok(this.orderSevice.changeStatusByOrder(dto));
    }

    @PostMapping("create-order")
    private ResponseEntity<?> createOrderByCounterSale(@RequestBody CounterSaleDTO dto) {
        return this.orderSevice.createOrderByCounterSale(dto.getOrderDTO(), dto.getProductItemDTOS());
    }


}
