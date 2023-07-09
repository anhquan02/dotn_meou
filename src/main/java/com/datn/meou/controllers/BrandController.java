package com.datn.meou.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datn.meou.entity.Brand;
import com.datn.meou.services.BrandService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;

    @GetMapping("")
    public String index(Model model, @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        Page<Brand> brands = brandService.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        model.addAttribute("brands", brands);
        model.addAttribute("brand", new Brand());
        model.addAttribute("name", _name);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", brands.getTotalPages());
        model.addAttribute("totalItem", brands.getTotalElements());
        return "brand/index";
    }

    @PostMapping("/save")
    public String saveBrand(Brand brand, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "brand/index";
        }
        brandService.saveBrand(brand.getName());
        return "redirect:/brand";
    }

    @GetMapping("/edit")
    public String editBrand(@RequestParam(required = false) Long id, Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        Page<Brand> brands = brandService.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        model.addAttribute("brands", brands);
        model.addAttribute("brand", brandService.findById(id));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", brands.getTotalPages());
        model.addAttribute("totalItem", brands.getTotalElements());
        return "brand/edit";
    }

    @PostMapping("/edit")
    public String editBrand(@RequestParam Long id, Brand brand, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "brand/index";
        }
        brand.setId(id);
        brandService.saveBrand(brand);
        return "redirect:/brand";
    }
}
