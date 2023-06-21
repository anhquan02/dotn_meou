package com.datn.meou.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderDTO extends BaseModel {

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
    private Double voucher;
    private String status;
    private String typeOrders;
}
