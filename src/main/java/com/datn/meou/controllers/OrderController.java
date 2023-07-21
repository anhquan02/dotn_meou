package com.datn.meou.controllers;


import com.datn.meou.model.OrderDTO;
import com.datn.meou.services.OrderSevice;
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
@RequestMapping("/order")
public class OrderController {

    private final OrderSevice orderSevice;


//    @GetMapping("/")
//    public String home(Model m, String code) {
//        if (code != null) {
//            return getAll(0, m, code);
//        }
////        } else if (idStatus != null) {
////            return getAll(0, m, null, idStatus);
////        }
//        return getAll(0, m, null);
//
//    }

    @GetMapping("/page/{pageno}")
    public String getAll(@PathVariable Integer pageno, Model model, @RequestParam(required = false) String code) {
        Page<OrderDTO> ordersPage;
        if (code != null && !code.isEmpty()) {
            ordersPage = this.orderSevice.searchByName(code, pageno, 2);
        }
//        else if (idStatus != null) {
//            ordersPage = this.orderSevice.searchByStatus(idStatus, pageno, 2);
//        }
        else {
            ordersPage = this.orderSevice.findAllPage(pageno, 2);
        }
        model.addAttribute("ordersPage", ordersPage);
        model.addAttribute("totalPages0", ordersPage.getTotalPages());
        model.addAttribute("totalItem0", ordersPage.getTotalElements());
        model.addAttribute("currentPage", pageno);
        return "order";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(@PathVariable Long id) {
        this.orderSevice.changeStatus2(id);
        return "redirect:/order/";
    }

    @GetMapping("/exit/{id}")
    public String exit(@PathVariable Long id) {
        this.orderSevice.changeStatus4(id);
        return "redirect:/order/";
    }

    @GetMapping("/prepare/{id}")
    public String prepare(@PathVariable Long id) {
        this.orderSevice.changeStatus3(id);
        return "redirect:/order/";
    }

    @GetMapping("/success/{id}")
    public String success(@PathVariable Long id) {
        this.orderSevice.changeStatus5(id);
        return "redirect:/order/";
    }


}
