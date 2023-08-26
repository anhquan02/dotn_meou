package com.datn.meou.controllers;

import com.datn.meou.entity.*;
import com.datn.meou.model.AccountDTO;
import com.datn.meou.model.ProductItemDTO;
import com.datn.meou.model.ProductItemDTOS;
import com.datn.meou.model.ImageDTOS;
import com.datn.meou.repository.ProductItemRepository;
import com.datn.meou.services.ProductItemSerivce;
import com.datn.meou.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/product-item")
@RequiredArgsConstructor
public class ProductItemController {
    private final ProductItemSerivce productItemSerivce;

    @PostMapping("search-countersale")
    private ResponseEntity<?> searchProductForCounterSale(@RequestBody ProductItemDTO dto) {
        return ResponseUtil.ok(this.productItemSerivce.searchProductForCounterSale(dto));
    }

    @PostMapping()
    private ResponseEntity<?> save(@Valid @RequestBody ProductItemDTOS dtos) {
        return ResponseUtil.ok(this.productItemSerivce.saveProductItem(dtos));
    }

    @PostMapping("/update-image")
    private ResponseEntity<?> updateImage(@Valid @RequestBody ImageDTOS dtos) {
        return ResponseUtil.ok(this.productItemSerivce.updateImage(dtos));
    }


    @PutMapping()
    private ResponseEntity<?> update(@Valid @RequestBody ProductItemDTOS dto) {
        return ResponseUtil.ok(this.productItemSerivce.updateProductItem(dto));
    }

    @DeleteMapping()
    private ResponseEntity<?> delete(@RequestParam List<Long> ids) {
        this.productItemSerivce.deleteProductItem(ids);
        return ResponseUtil.ok("Xóa thành công");
    }

    @GetMapping("product-id")
    private ResponseEntity<?> findProductItemByProductId(@RequestParam Long productId) {
        return ResponseUtil.ok(this.productItemSerivce.findProductItemByProjectId(productId));
    }

    @GetMapping("all-list")
    private ResponseEntity<?> findAllList() {
        return ResponseUtil.ok(this.productItemSerivce.findAllProductItemList());
    }

    @GetMapping("all-page")
    private ResponseEntity<?> findAllPage(Pageable pageable) {
        return ResponseUtil.ok(this.productItemSerivce.findAllProductItemPage(pageable));
    }

    @GetMapping("id")
    private ResponseEntity<?> findById(@RequestParam Long id) {
        return ResponseUtil.ok(this.productItemSerivce.findById(id));
    }

    @GetMapping("search-name")
    private ResponseEntity<?> findById(@RequestParam String name, Pageable pageable) {
        return ResponseUtil.ok(this.productItemSerivce.findByNameContaining(name, pageable));
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

//    @GetMapping("choose-types")
//    private ResponseEntity<?> chooseForOnline(@RequestParam Long soleId, @RequestParam Long brandId, @RequestParam Long
//            sizeId, @RequestParam Long productId, @RequestParam Long insoleId, @RequestParam Long colorId) {
//        return ResponseUtil.ok(this.productItemSerivce.chooseForOnline(soleId, brandId, sizeId, productId, insoleId, colorId));
//    }

}
