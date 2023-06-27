package com.datn.meou.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datn.meou.entity.Brand;
import com.datn.meou.entity.Product;
import com.datn.meou.services.ProductService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    public String index(Model model, @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        Page<Product> products = productService.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        model.addAttribute("products", products);
        model.addAttribute("product", new Product());
        model.addAttribute("name", _name);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalItem", products.getTotalElements());
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
    public String editProduct(@RequestParam("id") Long id, Model model, @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        model.addAttribute("products", productService.findAllProducts());
        model.addAttribute("product", productService.findById(id));
        Page<Product> products = productService.findByNameContaining(_name, PageRequest.of(currentPage, pageSize));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalItem", products.getTotalElements());
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
