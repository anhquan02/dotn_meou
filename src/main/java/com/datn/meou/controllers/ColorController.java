package com.datn.meou.controllers;

import java.util.List;
import java.util.Optional;

import com.datn.meou.model.ColorDTO;
import com.datn.meou.model.SoleDTO;
import com.datn.meou.util.ResponseUtil;
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
import com.datn.meou.entity.Color;
import com.datn.meou.services.ColorService;

import lombok.AllArgsConstructor;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/color")
public class ColorController {
    private final ColorService colorService;

    @PostMapping()
    private ResponseEntity<?> save(@Valid @RequestBody ColorDTO dto) {
        return ResponseUtil.ok(this.colorService.saveColor(dto));
    }

    @PutMapping()
    private ResponseEntity<?> update(@Valid @RequestBody ColorDTO dto) {
        return ResponseUtil.ok(this.colorService.updateColor(dto));
    }

    @DeleteMapping()
    private ResponseEntity<?> delete(@RequestParam List<Long> ids) {
        this.colorService.deleteColor(ids);
        return ResponseUtil.ok("Xóa thành công");
    }

    @GetMapping("all-list")
    private ResponseEntity<?> findAllList() {
        return ResponseUtil.ok(this.colorService.findAllColorList());
    }

    @GetMapping("all-page")
    private ResponseEntity<?> findAllPage(Pageable pageable) {
        return ResponseUtil.ok(this.colorService.findAllColorPage(pageable));
    }

    @GetMapping("id")
    private ResponseEntity<?> findById(@RequestParam Long id) {
        return ResponseUtil.ok(this.colorService.findById(id));
    }

    @GetMapping("search-name")
    private ResponseEntity<?> findById(@RequestParam String name, Pageable pageable) {
        return ResponseUtil.ok(this.colorService.findByNameContaining(name, pageable));
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
