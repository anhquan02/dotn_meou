package com.datn.meou.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "dotn_product_item")
public class ProductItem extends BaseEntity {
    private Integer productId;
    private Integer soleId;
    private Integer colorId;
    private Integer insoleId;
    private Integer brandId;
    private Integer sizeId;
    private Integer imgId;
    private Integer status;
    private Integer quantity;
}
