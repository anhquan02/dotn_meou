package com.datn.meou.controllers;

import java.util.Optional;

import com.datn.meou.entity.Sole;
import com.datn.meou.model.InsoleDTO;
import com.datn.meou.model.SoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.datn.meou.entity.Brand;
import com.datn.meou.entity.Insole;
import com.datn.meou.services.InsoleSerivce;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/insole")
@AllArgsConstructor
public class InsoleController {
    private final InsoleSerivce insoleSerivce;

    @GetMapping("")
    public Page<Insole> index(@RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        Page<Insole> insoles = insoleSerivce.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        return insoles;
    }

    @PostMapping("/addOrUpdate")
    public ResponseEntity<?> AddOrUpdate(@RequestBody InsoleDTO dto) {
        return ResponseEntity.ok(insoleSerivce.addOrUpdate(dto));
    }


    @GetMapping("/delete/{id}")
    public ResponseEntity<?> DeleteSole(@PathVariable Long id) {
        return ResponseEntity.ok(insoleSerivce.deteleInsole(id));
    }
}
