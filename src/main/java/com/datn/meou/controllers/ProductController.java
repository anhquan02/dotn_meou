package com.datn.meou.controllers;

import java.util.List;
import java.util.Optional;

import com.datn.meou.model.ColorDTO;
import com.datn.meou.model.ProductDTO;
import com.datn.meou.model.ProductItemDTO;
import com.datn.meou.repository.ProductRepository;
import com.datn.meou.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.datn.meou.entity.Brand;
import com.datn.meou.entity.Product;
import com.datn.meou.services.ProductService;

import lombok.AllArgsConstructor;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/v1/product")
@CrossOrigin
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ProductRepository productRepository;

    @PostMapping("advanced-search")
    private ResponseEntity<?> advancedSearchProduct(@RequestBody ProductDTO dto) {
        return ResponseUtil.ok(this.productRepository.advancedSearch(dto));
    }

    @PostMapping()
    private ResponseEntity<?> save(@Valid @RequestBody ProductDTO dto) {
        return ResponseUtil.ok(this.productService.saveProduct(dto));
    }

    @PutMapping()
    private ResponseEntity<?> update(@Valid @RequestBody ProductDTO dto) {
        return ResponseUtil.ok(this.productService.updateProduct(dto));
    }

    @DeleteMapping()
    private ResponseEntity<?> delete(@RequestParam List<Long> ids) {
        this.productService.deleteProduct(ids);
        return ResponseUtil.ok("Xóa thành công");
    }

    @GetMapping("all-list")
    private ResponseEntity<?> findAllList() {
        return ResponseUtil.ok(this.productService.findAllProductList());
    }

    @GetMapping("all-page")
    private ResponseEntity<?> findAllPage(Pageable pageable) {
        return ResponseUtil.ok(this.productService.findAllProductPage(pageable));
    }

    @GetMapping("id")
    private ResponseEntity<?> findById(@RequestParam Long id) {
        return ResponseUtil.ok(this.productService.findProductById(id));
    }

    @GetMapping("search-name")
    private ResponseEntity<?> findById(@RequestParam String name, Pageable pageable) {
        return ResponseUtil.ok(this.productService.findByNameContaining(name, pageable));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.append(error.getDefaultMessage()).append(",");
        });
        return ResponseUtil.badRequest(errors.toString());
    }

}
