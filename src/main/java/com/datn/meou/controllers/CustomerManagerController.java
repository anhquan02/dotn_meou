package com.datn.meou.controllers;

import com.datn.meou.model.ProductDTO;
import com.datn.meou.repository.ProductRepository;
import com.datn.meou.util.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerManagerController {

    private final ProductRepository productRepository;

    @PostMapping("home")
    private ResponseEntity<?> advancedSearchProduct(@RequestBody ProductDTO dto, Pageable pageable) {
        return ResponseUtil.ok(this.productRepository.advancedSearchPage(dto, pageable));
    }
}
