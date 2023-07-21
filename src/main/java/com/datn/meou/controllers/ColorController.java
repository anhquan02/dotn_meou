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
import com.datn.meou.entity.Color;
import com.datn.meou.services.ColorService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/color")
public class ColorController {
    private final ColorService colorService;

    @GetMapping("")
    public String index(Model model, @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        Page<Color> colors = colorService.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        model.addAttribute("colors", colors);
        model.addAttribute("color", new Color());
        model.addAttribute("name", _name);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", colors.getTotalPages());
        model.addAttribute("totalItem", colors.getTotalElements());
        return "color/index";
    }

    @PostMapping("/save")
    public String saveColor(Color color, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "color/index";
        }
        colorService.saveColor(color.getName());
        return "redirect:/color";
    }

    @GetMapping("/edit")
    public String editColor(@RequestParam(required = false) Long id, Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        Page<Color> colors = colorService.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        model.addAttribute("colors", colors);
        model.addAttribute("color", colorService.findById(id));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", colors.getTotalPages());
        model.addAttribute("totalItem", colors.getTotalElements());
        return "color/edit";
    }

    @PostMapping("/edit")
    public String editColor(@RequestParam Long id, Color color, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "color/index";
        }
        color.setId(id);
        colorService.saveColor(color);
        return "redirect:/color";
    }
}
