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
import com.datn.meou.entity.Size;
import com.datn.meou.services.SizeService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/size")
@AllArgsConstructor
public class SizeController {
    private final SizeService sizeService;

    @GetMapping("")
    public String index(Model model, @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        Page<Size> sizes = sizeService.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        model.addAttribute("size", new Size());
        model.addAttribute("sizes", sizes);
        model.addAttribute("name", _name);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", sizes.getTotalPages());
        model.addAttribute("totalItem", sizes.getTotalElements());
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
    public String editSize(@RequestParam Long id, Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        Page<Size> sizes = sizeService.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        model.addAttribute("sizes", sizeService.findAllSizes());
        model.addAttribute("size", sizeService.findById(id));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", sizes.getTotalPages());
        model.addAttribute("totalItem", sizes.getTotalElements());
        return "size/edit";
    }

    @PostMapping("/edit")
    public String editSize(@RequestParam Long id, Size size, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "size/index";
        }
        size.setId(id);
        sizeService.saveSize(size);
        return "redirect:/size";
    }
}
