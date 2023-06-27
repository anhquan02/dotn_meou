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
@Table(name = "dotn_exchange_item")
public class ExchangeItem extends BaseEntity {
    private Integer orderItemId;
    private Integer exchangeId;
    private Integer quantity;

    private Integer status;

}
