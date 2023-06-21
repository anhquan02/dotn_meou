package com.datn.meou.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datn.meou.entity.Insole;
import com.datn.meou.services.InsoleSerivce;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/insole")
@AllArgsConstructor
public class InsoleController {
    private final InsoleSerivce insoleSerivce;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("insole", new Insole());
        model.addAttribute("insoles", insoleSerivce.findAllInsoles());
        model.addAttribute("len_insole", insoleSerivce.findAllInsoles().size());
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
    public String editInsole(@RequestParam Long id, Model model) {
        model.addAttribute("insoles", insoleSerivce.findAllInsoles());
        model.addAttribute("len_insole", insoleSerivce.findAllInsoles().size());
        model.addAttribute("insole", insoleSerivce.findById(id));
        return "insole/edit";
    }
    @PostMapping("/edit")
    public String editInsole(@RequestParam Long id,Insole insole, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "insole/index";
        }
        insole.setId(id);
        insoleSerivce.saveInsole(insole);
        return "redirect:/insole";
    }
}
