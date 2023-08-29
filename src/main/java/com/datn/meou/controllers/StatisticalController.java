package com.datn.meou.controllers;

import com.datn.meou.model.ProductDTO;
import com.datn.meou.repository.ProductRepository;
import com.datn.meou.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/statistical")
@RequiredArgsConstructor
public class StatisticalController {

    private final ProductRepository productRepository;
    @PostMapping("statistical-monthly")
    private ResponseEntity<?> getStatisticalMonthly() {
        return ResponseUtil.ok(this.productRepository.getForMonthly());
    }

    @PostMapping("statistical-today")
    private ResponseEntity<?> getStatisticalToday() {
        return ResponseUtil.ok(this.productRepository.getForToday());
    }
}
