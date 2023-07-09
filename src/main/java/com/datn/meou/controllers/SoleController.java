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
import com.datn.meou.entity.Sole;
import com.datn.meou.services.SoleService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/sole")
public class SoleController {
    private final SoleService soleService;

    @GetMapping("")
    public String index(Model model, @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        Page<Sole> soles = soleService.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        model.addAttribute("sole", new Sole());
        model.addAttribute("soles", soleService.findAllSoles());
        model.addAttribute("name", _name);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", soles.getTotalPages());
        model.addAttribute("totalItem", soles.getTotalElements());
        return "sole/index";
    }

    @PostMapping("/save")
    public String saveSole(Sole sole, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "sole/index";
        }
        soleService.saveSole(sole.getName());
        return "redirect:/sole";
    }

    @GetMapping("/edit")
    public String editSole(@RequestParam(required = false) Long id, Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        Page<Sole> soles = soleService.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        model.addAttribute("soles", soleService.findAllSoles());
        model.addAttribute("sole", soleService.findById(id));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", soles.getTotalPages());
        model.addAttribute("totalItem", soles.getTotalElements());
        return "sole/edit";
    }

    @PostMapping("/edit")
    public String editSole(@RequestParam Long id, Sole sole, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "sole/index";
        }
        sole.setId(id);
        soleService.saveSole(sole);
        return "redirect:/sole";
    }
}
