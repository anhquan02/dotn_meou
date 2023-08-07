//package com.datn.meou.controllers;
//
//import com.datn.meou.entity.ExchangeItem;
//import com.datn.meou.model.ExchangeItemDTO;
//import com.datn.meou.services.ExchangeItemService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/exchange-item")
//@AllArgsConstructor
//public class ExchangeItemController {
//    private final ExchangeItemService exchangeItemService;
//
//    @GetMapping("")
//    public String index(Model model) {
//        model.addAttribute("exchangeItem", new ExchangeItem());
//        model.addAttribute("exchangeItems", exchangeItemService.getAllExchangeItem1());
//        return "exchangeItem/index";
//    }
//
//    @GetMapping("/exchangeItem")
//    public String indexExchange(Model model) {
//        model.addAttribute("exchangeItem", new ExchangeItem());
//        model.addAttribute("exchangeItems", exchangeItemService.getEchangeItemStatus());
//        return "exchangeItem/exchange-item";
//    }
//
//
//    @PostMapping("/save")
//    public String saveExchangeItem(ExchangeItemDTO dto){
//        exchangeItemService.InsertOrUpdateExchangeItem(dto);
//        return "redirect:/exchange-item";
//    }
//
//    @PostMapping("/update")
//    public String updateExchangeItem(ExchangeItemDTO dto, BindingResult bindingResult, Model model){
//        if(bindingResult.hasErrors()){
//            return "exchangeItem/index";
//        }
//        exchangeItemService.InsertOrUpdateExchangeItem(dto);
//        return "redirect:/exchange-item";
//    }
//
//    @GetMapping("/accept/{id}")
//    public String acceptExchangeItem(@PathVariable Long id)
//    {
//
//        exchangeItemService.acceptExchangeItem(id);
//        return "redirect:/exchange-item/exchangeItem";
//    }
//
//    @GetMapping("/cancel/{id}")
//    public String cancelExchangeItem(@PathVariable Long id)
//    {
//
////        exchangeItemService.CancelExchangeItem(id);
//        return "redirect:/exchange-item/exchangeItem";
//    }
//
////    @GetMapping("/getCustomerId")
////    public String getAllCustomerItem(@RequestParam(value = "CustomerId") Integer exchangeId, Model model){
////        model.addAttribute("exchangeItem", this.exchangeItemService.getAllExchangeItemByCustomerId(exchangeId));
////        return "exchange-item";
////
//}
