package com.datn.meou.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ExchangeItemDTO {
    private Long id;

    private String customerName;

    private String productName;

    private Integer typeOrder;

    private String typeOrderName;

    private Date createDateOrder;

    private Long orderItemId;
    private Long exchangeId;
    private Integer quantity;

    private Boolean deleted;

    private Float totalPrice;


    private Date updatedDate;

    private Date createdDate;

    private Integer status;

    private String statusName;

    private Long orderId;

    private Long accountId;
}
