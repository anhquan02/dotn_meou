package com.datn.meou.controllers;

import java.util.Optional;

import com.datn.meou.model.SoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.datn.meou.entity.Brand;
import com.datn.meou.entity.Sole;
import com.datn.meou.services.SoleService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/sole")
public class SoleController {
    private final SoleService soleService;

    @GetMapping("")
    public Page<Sole> index(@RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        Page<Sole> soles = soleService.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        return soles;
    }

    @PostMapping("/addOrUpdate")
    public ResponseEntity<?> AddOrUpdate(@RequestBody SoleDTO dto) {
        return ResponseEntity.ok(soleService.addOrUpdate(dto));
    }


    @GetMapping("/delete/{id}")
    public ResponseEntity<?> DeleteSole(@PathVariable Long id) {
        return ResponseEntity.ok(soleService.deteleSole(id));
    }

}
