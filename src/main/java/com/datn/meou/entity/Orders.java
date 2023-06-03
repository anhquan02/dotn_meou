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
@Table(name = "dotn_order")
public class Orders extends BaseEntity {
    private Integer accountId;
    private Integer voucherId;
    private String code;
    private Double totalPrice;
    private String paymentMethod;
    private Integer typeOrder;
    private String note;
    private Integer statusId;
    private String nameCustomer;
    private String phoneCustomer;
    private String addressCustomer;
    private String emailCustomer;


}
