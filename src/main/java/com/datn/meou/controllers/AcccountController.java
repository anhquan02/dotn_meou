package com.datn.meou.controllers;

import com.datn.meou.model.AccountDTO;
import com.datn.meou.model.LoginDTO;
import com.datn.meou.services.AccountService;
import com.datn.meou.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AcccountController {

    private final AccountService accountService;

    @PostMapping
    private ResponseEntity<?> save(@RequestBody AccountDTO dto) {
        return ResponseUtil.ok(this.accountService.createAccount(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody AccountDTO dto) {
        LoginDTO result = accountService.login(dto);
        if (result == null)
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}


