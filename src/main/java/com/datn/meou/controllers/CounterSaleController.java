package com.datn.meou.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/counter-sales")
public class CounterSaleController {

    @GetMapping("/page")
    public String getAll() {
        return "counter-sales";
    }
}
