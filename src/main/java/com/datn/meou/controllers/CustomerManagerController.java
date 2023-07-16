package com.datn.meou.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/customer-manager")
public class CustomerManagerController {

    public String index(Model model, @RequestParam() Long idCustomer){
      return null  ;
    };
}
