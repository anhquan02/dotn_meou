package com.datn.meou.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StatisticalDTOS {
    private Long productItemId;
    private String name;
    private BigDecimal priceProductItem;
    private BigDecimal totalQuantity;
    private Date saleDate;
    private BigDecimal totalPrice;
    private BigDecimal todayQuantity;
    private String fromDate;
    private String toDate;
    private String image;

    public StatisticalDTOS(Long productItemId,String name, BigDecimal priceProductItem, BigDecimal totalQuantity) {
        this.productItemId = productItemId;
        this.name = name;
        this.priceProductItem = priceProductItem;
        this.totalQuantity = totalQuantity;
    }

    public StatisticalDTOS(Date saleDate, BigDecimal todayQuantity, BigDecimal totalPrice) {
        this.saleDate = saleDate;
        this.totalPrice = totalPrice;
        this.todayQuantity = todayQuantity;
    }
}
