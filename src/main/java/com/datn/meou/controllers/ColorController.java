package com.datn.meou.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datn.meou.entity.Color;
import com.datn.meou.services.ColorService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/color")
public class ColorController {
    private final ColorService colorService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("colors", colorService.findAllColors());
        model.addAttribute("color", new Color());
        model.addAttribute("len_color", colorService.findAllColors().size());
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
    public String editColor(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("colors", colorService.findAllColors());
        model.addAttribute("len_color", colorService.findAllColors().size());
        model.addAttribute("color", colorService.findById(id));
        return "color/edit";
    }
    @PostMapping("/edit")
    public String editColor(@RequestParam Long id,Color color, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "color/index";
        }
        color.setId(id);
        colorService.saveColor(color);
        return "redirect:/color";
    }
}
