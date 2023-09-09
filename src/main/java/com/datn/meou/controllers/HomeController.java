package com.datn.meou.controllers;

import com.datn.meou.model.AccountDTO;
import com.datn.meou.model.ProductDTO;
import com.datn.meou.repository.ProductRepository;
import com.datn.meou.services.AccountService;
import com.datn.meou.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class HomeController {

    private final ProductRepository productRepository;

    private final AccountService accountService;

    @PostMapping("/home")
    private ResponseEntity<?> home(@RequestBody ProductDTO dto, Pageable pageable) {
        return ResponseUtil.ok(this.productRepository.advancedSearchPage(dto, pageable));
    }

    @PostMapping("/change-password")
    private ResponseEntity<?> changePassword(@RequestBody AccountDTO dto) {
        return ResponseUtil.ok(this.accountService.changePassward(dto));
    }
}
