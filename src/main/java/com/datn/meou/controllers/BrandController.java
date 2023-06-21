package com.datn.meou.controllers;
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
    public String index(Model model) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("brands", brandService.findAllBrands());
        model.addAttribute("len_brand", brandService.findAllBrands().size());
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
    public String editBrand(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("brands", brandService.findAllBrands());
        model.addAttribute("len_brand", brandService.findAllBrands().size());
        model.addAttribute("brand", brandService.findById(id));
        return "brand/index";
    }

    @PostMapping("/edit")
    public String editBrand(@RequestParam Long id,Brand brand, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "brand/index";
        }
        brand.setId(id);
        brandService.saveBrand(brand);
        return "redirect:/brand";
    }
}
