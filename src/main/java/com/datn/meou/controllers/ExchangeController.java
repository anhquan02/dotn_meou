package com.datn.meou.controllers;


import com.datn.meou.model.ExchangeDTO;
import com.datn.meou.model.ExchangeItemDTO;
import com.datn.meou.services.ExchangeService;
import com.datn.meou.util.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exchange")
@AllArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;


    @PostMapping("/save")
    public String createExchange(ExchangeDTO dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "order/index";
        }

        exchangeService.createExchange(dto);

        return "order/index";
    }

    @PostMapping("/update")
    public String updateExchange(ExchangeDTO dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "order/index";
        }

        exchangeService.updateExchange(dto);

        return "order/index";
    }
}
