package com.datn.meou.controller;


import com.datn.meou.entity.Orders;
import com.datn.meou.service.OrderSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderSevice orderSevice;

    @GetMapping("/page/{pageno}")
    public String getAll(@PathVariable Integer pageno, Model model) {
        Page<Orders> ordersList = this.orderSevice.findAll(pageno, 2);
        Page<Orders> ordersList2 = this.orderSevice.getStatus2(pageno, 2);
        Page<Orders> ordersList3 = this.orderSevice.getStatus3(pageno, 2);
        Page<Orders> ordersList4 = this.orderSevice.getStatus4(pageno, 2);
        Page<Orders> ordersList5 = this.orderSevice.getStatus5(pageno, 2);
        model.addAttribute("ordersList", ordersList);
        model.addAttribute("ordersList2", ordersList2);
        model.addAttribute("ordersList3", ordersList3);
        model.addAttribute("ordersList4", ordersList4);
        model.addAttribute("ordersList5", ordersList5);
        model.addAttribute("totalPages", ordersList.getTotalPages());
        model.addAttribute("totalItem", ordersList.getTotalElements());
        model.addAttribute("totalPages", ordersList2.getTotalPages());
        model.addAttribute("totalItem", ordersList2.getTotalElements());
        model.addAttribute("totalPages", ordersList3.getTotalPages());
        model.addAttribute("totalItem", ordersList3.getTotalElements());
        model.addAttribute("totalPages", ordersList4.getTotalPages());
        model.addAttribute("totalItem", ordersList4.getTotalElements());
        model.addAttribute("totalPages", ordersList5.getTotalPages());
        model.addAttribute("totalItem", ordersList5.getTotalElements());
        model.addAttribute("currentPage", pageno);
        return "order";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(@PathVariable Long id) {
        this.orderSevice.changeStatus2(id);
        return "redirect:/order";
    }

    @GetMapping("/exit/{id}")
    public String exit(@PathVariable Long id) {
        this.orderSevice.changeStatus4(id);
        return "redirect:/order";
    }

    @GetMapping("/prepare/{id}")
    public String prepare(@PathVariable Long id) {
        this.orderSevice.changeStatus3(id);
        return "redirect:/order";
    }

    @GetMapping("/success/{id}")
    public String success(@PathVariable Long id) {
        this.orderSevice.changeStatus5(id);
        return "redirect:/order";
    }


}
