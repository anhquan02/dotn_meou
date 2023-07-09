package com.datn.meou.controllers;

import com.datn.meou.entity.Account;
import com.datn.meou.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/staff")
public class AcccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("")
    public String Index(Model model, @RequestParam(value = "page", defaultValue = "0")Integer pageNum){
        Integer size=10;
        model.addAttribute("accounts",accountService.getListStaff(pageNum,size).getContent());
        return "staff/index";
    }

    @GetMapping("/create")
    public String viewAccount(Model model){
        model.addAttribute("account", new Account());
        return "staff/create";
    }
    @PostMapping("/store")
    public String storeAccount(Account account, BindingResult result, Model model, @RequestParam(required = false) Long id) {
        if (result.hasErrors()) {
            return "staff/create";
        }
        account.setRoleId(2);
//        System.out.println(account);
        accountService.saveAccount(account);
        return "redirect:/staff";
    }
    @GetMapping("/edit/{id}")
    public String getById(@PathVariable("id")Long id, Model model){
        model.addAttribute("account",accountService.findById(id));
        return  "staff/edit";
    }

    @PostMapping("/update")
    public String updateAccount(@ModelAttribute("account") Account account, BindingResult result, Model model){
        if(result.hasErrors()){
            return "staff/edit";
        }
//        System.out.println("x");
//        System.out.println(account.getId());
        accountService.saveAccount(account);
        return "redirect:/staff";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id")Long id ){
        accountService.deleteAccount(id);
        return "redirect:/staff";
    }
}


