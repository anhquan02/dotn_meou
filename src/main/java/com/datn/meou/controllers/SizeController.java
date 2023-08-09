package com.datn.meou.controllers;

import java.util.Optional;

import com.datn.meou.model.SizeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.datn.meou.entity.Brand;
import com.datn.meou.entity.Size;
import com.datn.meou.services.SizeService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/size")
@AllArgsConstructor
public class SizeController {
    private final SizeService sizeService;

    @GetMapping("")
    public Page<Size> index(@RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        Page<Size> sizes = sizeService.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        return sizes;
    }

    @PostMapping("/addOrUpdate")
    public ResponseEntity<?> AddOrUpdate(@RequestBody SizeDTO dto) {
        return ResponseEntity.ok(sizeService.addOrUpdate(dto));
    }


    @GetMapping("/delete/{id}")
    public ResponseEntity<?> DeleteSole(@PathVariable Long id) {
        return ResponseEntity.ok(sizeService.deteleSize(id));
    }


}
