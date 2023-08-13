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
public class TransactionDTO extends BaseModel {

    private Long orderId;
    private BigDecimal totalPrice;

    private Long accountId;
    private String codeOrder;
    private String nameCustomer;
    private String phoneCustomer;
    private String typeOrders;
    private Integer type;

    public TransactionDTO(Long id, String codeOrder, Date createdDate, String nameCustomer, String phoneCustomer, Integer type, BigDecimal totalPrice) {
        this.id = id;
        this.codeOrder = codeOrder;
        this.createdDate = createdDate;
        this.nameCustomer = nameCustomer;
        this.phoneCustomer = phoneCustomer;
        this.type = type;
        this.totalPrice = totalPrice;
    }
}
