package com.datn.meou.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Integer productDetailId;
    private Integer orderId;
    private String nameProduct;
    private String description;
    private Integer quantityOrder;
    private BigDecimal priceSell;
    private String colorProduct;
    private String brandProduct;
    private String sizeProduct;
    private String soleProduct;
    private String insoleProduct;
    private String image;
}
