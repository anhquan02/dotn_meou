package com.datn.meou.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "dotn_product_item")
public class ProductItem extends BaseEntity {
    private String name;
    private Integer productId;
    private Long soleId;
    private Long colorId;
    private Long insoleId;
    private Long brandId;
    private Long sizeId;
    private Long imgId;
    private Integer status;
    private Integer quantity;
}
