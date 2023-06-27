package com.datn.meou.controllers;

import lombok.AllArgsConstructor;

import java.util.List;
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
import com.datn.meou.entity.ProductItem;
import com.datn.meou.entity.Brand;
import com.datn.meou.entity.Product;
import com.datn.meou.entity.Color;
import com.datn.meou.entity.Sole;
import com.datn.meou.entity.Insole;
import com.datn.meou.entity.Size;
import com.datn.meou.services.ProductItemSerivce;

@Controller
@AllArgsConstructor
@RequestMapping("/product-detail")
public class ProductItemController {
    private final ProductItemSerivce productItemSerivce;

    @GetMapping("")
    public String index(Model model, @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, @RequestParam("name") Optional<String> name,
            @RequestParam("brandId") Optional<String> brandId, @RequestParam("soleId") Optional<String> soleId,
            @RequestParam("insoleId") Optional<String> insoleId, @RequestParam("colorId") Optional<String> colorId,
            @RequestParam("status") Optional<String> status, @RequestParam("sizeId") Optional<String> sizeId) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        String _name = name.orElse("");
        String _brandId = brandId.orElse("");
        String _soleId = soleId.orElse("");
        String _insoleId = insoleId.orElse("");
        String _colorId = colorId.orElse("");
        String _status = status.orElse("");
        String _sizeId = sizeId.orElse("");

        List<Size> sizes = productItemSerivce.findAllSizes();
        List<Product> products = productItemSerivce.findAllProducts();
        List<Color> colors = productItemSerivce.findAllColors();
        List<Insole> insoles = productItemSerivce.findAllInsoles();
        List<Sole> soles = productItemSerivce.findAllSoles();
        List<Brand> brands = productItemSerivce.findAllBrands();

        Page<ProductItem> productItems = productItemSerivce.findByNameContaining(_name,
                PageRequest.of(currentPage, pageSize));
        // Page<ProductItem> productItems = productItemSerivce.findFilter(_name, _brandId, _soleId, _insoleId, _colorId,
        //         _sizeId, _status, PageRequest.of(currentPage, pageSize));
        model.addAttribute("sizes", sizes);
        model.addAttribute("products", products);
        model.addAttribute("colors", colors);
        model.addAttribute("insoles", insoles);
        model.addAttribute("soles", soles);
        model.addAttribute("brands", brands);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("productItems", productItems);
        model.addAttribute("totalPages", productItems.getTotalPages());
        model.addAttribute("totalItem", productItems.getTotalElements());
        model.addAttribute("name", _name);
        return "product-detail/index";
    }

    @GetMapping("/add")
    public String addProductItem(Model model) {
        List<Size> sizes = productItemSerivce.findAllSizes();
        List<Color> colors = productItemSerivce.findAllColors();
        List<Insole> insoles = productItemSerivce.findAllInsoles();
        List<Sole> soles = productItemSerivce.findAllSoles();
        List<Brand> brands = productItemSerivce.findAllBrands();
        List<Product> products = productItemSerivce.findAllProducts();

        model.addAttribute("productDetail", new ProductItem());
        model.addAttribute("sizes", sizes);
        model.addAttribute("products", products);
        model.addAttribute("colors", colors);
        model.addAttribute("insoles", insoles);
        model.addAttribute("soles", soles);
        model.addAttribute("brands", brands);

        return "product-detail/form";
    }

    @PostMapping("/save")
    public String saveProductItem(ProductItem productItem, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "product-detail/form";
        }
        productItemSerivce.saveProductItem(productItem);
        return "redirect:/product-detail";
    }

    @GetMapping("/edit")
    public String editProductItem(@RequestParam("id") Long id, Model model) {
        List<Size> sizes = productItemSerivce.findAllSizes();
        List<Color> colors = productItemSerivce.findAllColors();
        List<Insole> insoles = productItemSerivce.findAllInsoles();
        List<Sole> soles = productItemSerivce.findAllSoles();
        List<Brand> brands = productItemSerivce.findAllBrands();
        List<Product> products = productItemSerivce.findAllProducts();

        model.addAttribute("productDetail", productItemSerivce.findById(id));
        model.addAttribute("sizes", sizes);
        model.addAttribute("products", products);
        model.addAttribute("colors", colors);
        model.addAttribute("insoles", insoles);
        model.addAttribute("soles", soles);
        model.addAttribute("brands", brands);

        return "product-detail/edit";
    }

    @PostMapping("/edit")
    public String editProductItem(@RequestParam("id") Long id, ProductItem productItem, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "product-detail/form";
        }
        productItem.setId(id);
        productItemSerivce.saveProductItem(productItem);
        return "redirect:/product-detail";
    }

}
