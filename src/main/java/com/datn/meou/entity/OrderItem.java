package com.datn.meou.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "dotn_order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer productDetailId;
    private Integer orderId;
    private String nameProduct;
    private String description;
    private Integer quantityOrder;
    private Double priceSell;
    private String colorProduct;
    private String brandProduct;
    private String sizeProduct;
    private String soleProduct;
    private String insoleProduct;

}
