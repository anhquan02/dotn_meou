package com.datn.meou.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datn.meou.entity.Size;
import com.datn.meou.services.SizeService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/size")
@AllArgsConstructor
public class SizeController {
    private final SizeService sizeService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("size", new Size());
        model.addAttribute("sizes", sizeService.findAllSizes());
        model.addAttribute("len_size", sizeService.findAllSizes().size());
        return "size/index";
    }

    @PostMapping("/save")
    public String saveSize(Size size, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "size/index";
        }
        sizeService.saveSize(size.getName());
        return "redirect:/size";
    }

    @GetMapping("/edit")
    public String editSize(@RequestParam Long id, Model model) {
        model.addAttribute("sizes", sizeService.findAllSizes());
        model.addAttribute("len_size", sizeService.findAllSizes().size());
        model.addAttribute("size", sizeService.findById(id));
        return "size/edit";
    }
    @PostMapping("/edit")
    public String editSize(@RequestParam Long id,Size size, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "size/index";
        }
        size.setId(id);
        sizeService.saveSize(size);
        return "redirect:/size";
    }
}
