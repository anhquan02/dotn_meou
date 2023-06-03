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
@Table(name = "dotn_transaction")
public class Transaction extends BaseEntity {
    private Integer orderId;
    private Double totalPrice;
    private Integer accountId;
}
