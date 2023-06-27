package com.datn.meou.controllers;

import com.datn.meou.entity.ExchangeItem;
import com.datn.meou.model.ExchangeItemDTO;
import com.datn.meou.services.ExchangeItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/exchange-item")
@AllArgsConstructor
public class ExchangeItemController {
    private final ExchangeItemService exchangeItemService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("exchangeItem", new ExchangeItem());
        model.addAttribute("exchangeItems", exchangeItemService.getAllExchangeItem());
        return "exchange-item/index";
    }


    @PostMapping("/save")
    public String saveExchangeItem(ExchangeItemDTO dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "exchange/index";
        }
        exchangeItemService.InsertOrUpdateExchangeItem(dto);
        return "redirect:/exchange";
    }

    @PostMapping("/update")
    public String updateExchangeItem(ExchangeItemDTO dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "exchange/index";
        }
        exchangeItemService.InsertOrUpdateExchangeItem(dto);
        return "redirect:/exchange";
    }

    @PostMapping("/accept")
    public String acceptExchangeItem(@RequestParam Long id, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "exchange/index";
        }
        exchangeItemService.acceptExchangeItem(id);
        return "redirect:/exchange";
    }

    @PostMapping("/cancel")
    public String cancelExchangeItem(@RequestParam Long id, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "exchange-item/index";
        }
        exchangeItemService.CancelExchangeItem(id);
        return "redirect:/exchange";
    }

//    @GetMapping("/getCustomerId")
//    public String getAllCustomerItem(@RequestParam(value = "CustomerId") Integer exchangeId, Model model){
//        model.addAttribute("exchangeItem", this.exchangeItemService.getAllExchangeItemByCustomerId(exchangeId));
//        return "exchange-item";
//    }
}
