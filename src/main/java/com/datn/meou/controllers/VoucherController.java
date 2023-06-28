package com.datn.meou.controllers;

import com.datn.meou.entity.Voucher;
import com.datn.meou.services.VoucherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }
    @GetMapping("/vouchers")
    public String getAllVouchers(@RequestParam(defaultValue = "0") int page, Model model) {
        PageRequest pageable = PageRequest.of(page, 2);
        Page<Voucher> voucherPage = voucherService.getAllVouchers(pageable);

        model.addAttribute("vouchers", voucherPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", voucherPage.getTotalPages());

        return "voucher/voucher-list";
    }
    @GetMapping("/vouchers/{id}")
    public String getVoucherById(@PathVariable("id") Long id, Model model) {
        Voucher voucher = voucherService.getVoucherById(id);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher-details";
    }
    @GetMapping("/vouchers/new")
    public String showVoucherForm(Model model) {
        Voucher voucher = new Voucher();
        model.addAttribute("voucher", voucher);
        return "voucher/voucher-form";
    }
    @PostMapping("/vouchers")
    public String saveVoucher(@ModelAttribute("voucher") Voucher voucher) {
        voucherService.saveVoucher(voucher);
        return "redirect:/vouchers/search?name=&page=0";
    }

    @GetMapping("/vouchers/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Voucher voucher = voucherService.getVoucherById(id);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher-form";
    }
    @PostMapping("/vouchers/update/{id}")
    public String updateVoucher(@PathVariable("id") Long id, @ModelAttribute("voucher") Voucher voucher) {
        voucherService.updateVoucher(voucher);
        return "redirect:/vouchers/search?name=&page=0";
    }
    @GetMapping("/vouchers/{id}/delete")
    public String deleteVoucher(@PathVariable Long id) {
        voucherService.deleteVoucher(id);
        return "redirect:/vouchers/search?name=&page=0";
    }
    @GetMapping("/vouchers/search")
    public String searchVouchers(@RequestParam("name") String name,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model) {
        PageRequest pageable = PageRequest.of(page, 2);
        Page<Voucher> voucherPage = voucherService.searchVouchersByName(name, pageable);

        model.addAttribute("vouchers", voucherPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", voucherPage.getTotalPages());

        return "voucher/voucher-list";
    }
}
