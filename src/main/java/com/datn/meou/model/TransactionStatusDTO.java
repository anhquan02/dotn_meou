package com.datn.meou.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TransactionStatusDTO extends BaseModel {

    private Long statusId;
    private Long orderId;
    private String note;
    private Long accountId;
    private String username;
}
