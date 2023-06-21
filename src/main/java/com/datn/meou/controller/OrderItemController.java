package com.datn.meou.controller;


import com.datn.meou.service.OrderItemService;
import com.datn.meou.service.OrderSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderSevice orderSevice;
    private final OrderItemService orderItemService;

    @GetMapping("/getId")
    public String getById(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("orders", this.orderSevice.getbyId(id));
        model.addAttribute("item", this.orderItemService.getByIdOrder(id));
        return "order-item";
    }
}
