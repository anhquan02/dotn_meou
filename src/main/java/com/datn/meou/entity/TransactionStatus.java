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
@Table(name = "dotn_transaction_status")
public class TransactionStatus extends BaseEntity {
    private Long statusId;
    private Long orderId;
    private String note;
    private Long accountId;

}
