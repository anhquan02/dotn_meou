package com.datn.meou.controllers;


import com.datn.meou.services.OrderItemService;
import com.datn.meou.services.OrderSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-item")
public class OrderItemController {

    private final OrderSevice orderSevice;
    private final OrderItemService orderItemService;

    @GetMapping("all")
    private ResponseEntity<?> findAllPage(@RequestParam Long idOrder) {
        return orderItemService.getByIdOrder(idOrder);
    }
}
