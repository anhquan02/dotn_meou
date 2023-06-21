package com.datn.meou.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datn.meou.entity.Product;
import com.datn.meou.services.ProductService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        model.addAttribute("product", new Product());
        model.addAttribute("len_product", productService.findAllProducts().size());
        return "product/index";
    }

    @PostMapping("/save")
    public String saveProduct(Product product, BindingResult result, Model model,
            @RequestParam(required = false) Long id) {
        if (result.hasErrors()) {
            return "product/index";
        }
        if (id != null) {
            product.setId(id);
        }

        productService.saveProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/edit")
    public String editProduct(@RequestParam("id") Long id, Model model) {
        model.addAttribute("products", productService.findAllProducts());
        model.addAttribute("len_product", productService.findAllProducts().size());
        model.addAttribute("product", productService.findById(id));
        return "product/edit";
    }

    @PostMapping("/edit")
    public String editProduct(@RequestParam("id") Long id, Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "product/edit";
        }
        product.setId(id);
        productService.saveProduct(product);
        return "redirect:/product";
    }
}
