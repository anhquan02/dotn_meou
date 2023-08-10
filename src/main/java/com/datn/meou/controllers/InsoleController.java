package com.datn.meou.controllers;

import java.util.List;
import java.util.Optional;

import com.datn.meou.entity.Sole;
import com.datn.meou.model.InsoleDTO;
import com.datn.meou.model.SoleDTO;
import com.datn.meou.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.datn.meou.entity.Brand;
import com.datn.meou.entity.Insole;
import com.datn.meou.services.InsoleSerivce;

import lombok.AllArgsConstructor;

import javax.validation.Valid;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/insole")
@RequiredArgsConstructor
public class InsoleController {
    private final InsoleSerivce insoleSerivce;

    @PostMapping()
    private ResponseEntity<?> save(@Valid @RequestBody InsoleDTO dto) {
        return ResponseUtil.ok(this.insoleSerivce.saveInsole(dto));
    }

    @PutMapping()
    private ResponseEntity<?> update(@Valid @RequestBody InsoleDTO dto) {
        return ResponseUtil.ok(this.insoleSerivce.updateInsole(dto));
    }

    @DeleteMapping()
    private ResponseEntity<?> delete(@RequestParam List<Long> ids) {
        this.insoleSerivce.deleteInsole(ids);
        return ResponseUtil.ok("Xóa thành công");
    }

    @GetMapping("all-list")
    private ResponseEntity<?> findAllList() {
        return ResponseUtil.ok(this.insoleSerivce.findAllInsoleList());
    }

    @GetMapping("all-page")
    private ResponseEntity<?> findAllPage(Pageable pageable) {
        return ResponseUtil.ok(this.insoleSerivce.findAllInsolePage(pageable));
    }

    @GetMapping("id")
    private ResponseEntity<?> findById(@RequestParam Long id) {
        return ResponseUtil.ok(this.insoleSerivce.findById(id));
    }

    @GetMapping("search-name")
    private ResponseEntity<?> findById(@RequestParam String name, Pageable pageable) {
        return ResponseUtil.ok(this.insoleSerivce.findByNameContaining(name, pageable));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.append(error.getDefaultMessage()).append(",");
        });
        return ResponseUtil.badRequest(errors.toString());
    }
}
