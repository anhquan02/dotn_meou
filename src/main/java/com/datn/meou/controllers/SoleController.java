package com.datn.meou.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datn.meou.entity.Sole;
import com.datn.meou.services.SoleService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/sole")
public class SoleController {
    private final SoleService soleService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("sole", new Sole());
        model.addAttribute("soles", soleService.findAllSoles());
        model.addAttribute("len_sole", soleService.findAllSoles().size());
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
    public String editSole(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("soles", soleService.findAllSoles());
        model.addAttribute("len_sole", soleService.findAllSoles().size());
        model.addAttribute("sole", soleService.findById(id));
        return "sole/edit";
    }

    @PostMapping("/edit")
    public String editSole(@RequestParam Long id,Sole sole, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "sole/index";
        }
        sole.setId(id);
        soleService.saveSole(sole);
        return "redirect:/sole";
    }
}
