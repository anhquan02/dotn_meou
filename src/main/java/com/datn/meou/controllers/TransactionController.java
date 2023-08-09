package com.datn.meou.controllers;


import com.datn.meou.model.TransactionDTO;
import com.datn.meou.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

//    @GetMapping("/page/{pageno}")
//    public String getAll(@PathVariable Integer pageno, Model model, @RequestParam(required = false) String code) {
//        Page<TransactionDTO> page;
//        if (code != null && !code.isEmpty()) {
//            page = this.transactionService.findAllByOrderName(code, pageno, 2);
//        } else {
//            page = this.transactionService.findAll(pageno, 2);
//        }
//        model.addAttribute("transaction", page);
//        model.addAttribute("currentPage", pageno);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItem", page.getTotalElements());
//        return "transaction";
//    }


}
