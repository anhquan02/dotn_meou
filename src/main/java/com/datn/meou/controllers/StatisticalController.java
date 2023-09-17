package com.datn.meou.controllers;

import com.datn.meou.model.ProductDTO;
import com.datn.meou.model.StatisticalDTOS;
import com.datn.meou.repository.ProductItemRepository;
import com.datn.meou.repository.ProductRepository;
import com.datn.meou.services.StatisticalService;
import com.datn.meou.util.DataUtil;
import com.datn.meou.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/statistical")
@RequiredArgsConstructor
public class StatisticalController {

    private final ProductRepository productRepository;

    private  final ProductItemRepository productItemRepository;

    private final StatisticalService statisticalService;

    @PostMapping("statistical-monthly")
    private ResponseEntity<?> getStatisticalMonthly() {
        return ResponseUtil.ok(this.productRepository.getForMonthly());
    }

    @PostMapping("statistical-today")
    private ResponseEntity<?> getStatisticalToday() {
        return ResponseUtil.ok(this.productRepository.getForToday());
    }

    @PostMapping("top-month-sales")
    private ResponseEntity<?> getTopMonthSale() {
        return ResponseUtil.ok(this.statisticalService.topSales());
    }

    @PostMapping("statistical-by-date")
    private ResponseEntity<?> getStatisticalByDate(@RequestBody  StatisticalDTOS dto) {
        Date fromDate = null;
        Date toDate = null;
        if(!DataUtil.isNullObject(dto.getFromDate()) && !DataUtil.isNullObject(dto.getToDate())){
             fromDate = new Date(dto.getFromDate());
             toDate = new Date(dto.getToDate());
        }
        return ResponseUtil.ok(this.productItemRepository.getStatisticalByDate(fromDate, toDate));
    }


}
