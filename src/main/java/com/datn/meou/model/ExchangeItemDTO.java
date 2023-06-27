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

    private String name;

    private Integer orderItemId;
    private Integer exchangeId;
    private Integer quantity;

    private Boolean deleted;


    private Date updatedDate;

    private Date createdDate;
}
