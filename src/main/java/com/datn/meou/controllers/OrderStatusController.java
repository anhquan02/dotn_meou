package com.datn.meou.controllers;


import com.datn.meou.model.OrderStatusDTO;
import com.datn.meou.services.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

//    @GetMapping("/")
//    public String home(Model m) {
//        return getAll(0, m);
//    }
//
//    @GetMapping("/page/{pageno}")
//    public String getAll(@PathVariable Integer pageno, Model model) {
//        Page<OrderStatusDTO> page = this.orderStatusService.findAll(pageno, 2);
//
//        model.addAttribute("orderStatus", page);
//        model.addAttribute("currentPage", pageno);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItem", page.getTotalElements());
//
//        return "order-status";
//    }
//
//    @GetMapping("/convert")
//    public String convertForm(Model model) {
//        OrderStatusDTO dto = new OrderStatusDTO();
//        model.addAttribute("dto", dto);
//        return "form-order-status";
//    }
//
//    @PostMapping("/create")
//    public String save(@ModelAttribute("dto") @Valid OrderStatusDTO dto, BindingResult result) {
//        if (result.hasErrors()) {
//            return "form-order-status";
//        }
//        this.orderStatusService.save(dto);
//        return "redirect:/convert";
//    }
//
//    @PostMapping("/update")
//    public String update(@ModelAttribute("dto") OrderStatusDTO dto) {
//        this.orderStatusService.update(dto);
//        Long id = dto.getId();
//        return "redirect:/edit?id=" + id;
//    }
//
//    @GetMapping("/edit")
//    public String getById(@RequestParam(value = "id") Long id, Model model) {
//        model.addAttribute("dto", this.orderStatusService.getbyId(id));
//        return "edit-order-status";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleted(@PathVariable Long id) {
//        this.orderStatusService.delete(id);
//        return "redirect:/";
//    }

}
