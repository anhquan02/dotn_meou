package com.datn.meou.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderItem1DTO {
    private Long id;
    private Long idCustomer;

    private String nameCustomer;

    private Long idProduct;

    private String nameProduct;

    private String createdDate;

    private String createdBy;

    private Integer typeOrder;

    private Float price;
}
