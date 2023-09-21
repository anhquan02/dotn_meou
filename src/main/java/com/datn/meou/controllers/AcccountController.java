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
    @GetMapping("search-customer")
    private ResponseEntity<?> getAllCustomer(@RequestParam String phone) {
        return ResponseUtil.ok(this.accountService.getAllCustomer(phone));
    }

    @PostMapping("/register")
    private ResponseEntity<?> register(@RequestBody AccountDTO dto) {
        return ResponseUtil.ok(this.accountService.register(dto));
    }

    @PostMapping("/update-staff")
    private ResponseEntity<?> updateStaff(@RequestBody AccountDTO dto) {
        return ResponseUtil.ok(this.accountService.updateAccount(dto));
    }

    @GetMapping("/get-staff-information")
    private ResponseEntity<?> getStaffInformation() {
        return ResponseUtil.ok(this.accountService.getInfomationAccounts());
    }

    @GetMapping("/get-staff-by-name")
    private ResponseEntity<?> getStaffByName(@RequestParam String name) {
        return ResponseUtil.ok(this.accountService.getInfomationAccountsByName(name));
    }

    @PostMapping("/update-staff-by-admin")
    private ResponseEntity<?> updateStaffByAdmin(@RequestBody AccountDTO dto) {
        return ResponseUtil.ok(this.accountService.updateAccountByAdmin(dto));
    }
}


