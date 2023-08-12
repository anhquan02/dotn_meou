package com.datn.meou.controllers;


import com.datn.meou.model.BrandDTO;
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

    @GetMapping("all-page")
    private ResponseEntity<?> findAllPage(@RequestBody OrderDTO dto, Pageable pageable) {
        return ResponseUtil.ok(this.orderSevice.findAll(dto, pageable));
    }

    @PostMapping("change-status")
    private ResponseEntity<?> changeStatusByOrder(@RequestParam Long idOrder, @RequestParam Long idStatus, @RequestParam(required = false) String note) {
        return ResponseUtil.ok(this.orderSevice.changeStatusByOrder(idOrder, idStatus, note));
    }

    @PostMapping("create-order")
    private ResponseEntity<?> createOrderByCounterSale(@RequestBody OrderDTO dto) {
        return this.orderSevice.createOrderByCounterSale(dto);
    }


}
