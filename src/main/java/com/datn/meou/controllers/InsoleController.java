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
import com.datn.meou.entity.Insole;
import com.datn.meou.services.InsoleSerivce;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/insole")
@AllArgsConstructor
public class InsoleController {
    private final InsoleSerivce insoleSerivce;

    @GetMapping("")
    public String index(Model model, @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        Page<Insole> insoles = insoleSerivce.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        model.addAttribute("insole", new Insole());
        model.addAttribute("insoles", insoles);
        model.addAttribute("name", _name);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", insoles.getTotalPages());
        model.addAttribute("totalItem", insoles.getTotalElements());
        return "insole/index";
    }

    @PostMapping("/save")
    public String saveInsole(Insole insole, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "insole/index";
        }
        insoleSerivce.saveInsole(insole.getName());
        return "redirect:/insole";
    }

    @GetMapping("/edit")
    public String editInsole(@RequestParam Long id, Model model, @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        Page<Insole> insoles = insoleSerivce.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        model.addAttribute("insoles", insoles);
        model.addAttribute("insole", insoleSerivce.findById(id));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", insoles.getTotalPages());
        model.addAttribute("totalItem", insoles.getTotalElements());
        return "insole/edit";
    }

    @PostMapping("/edit")
    public String editInsole(@RequestParam Long id, Insole insole, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "insole/index";
        }
        insole.setId(id);
        insoleSerivce.saveInsole(insole);
        return "redirect:/insole";
    }
}
