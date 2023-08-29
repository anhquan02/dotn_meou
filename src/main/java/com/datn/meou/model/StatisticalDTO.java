package com.datn.meou.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StatisticalDTO {
    private Integer index;
    private Integer monthQuantity;
    private Double monthPrice;
    private Integer todayQuantity;
    private Double todayPrice;
    private BigDecimal quantityOrder;

    public StatisticalDTO(Integer monthQuantity, Double monthPrice, BigDecimal quantityOrder) {
        this.monthQuantity = monthQuantity;
        this.monthPrice = monthPrice;
        this.quantityOrder = quantityOrder;
    }

    public StatisticalDTO(Integer todayQuantity, Double todayPrice) {
        this.todayQuantity = todayQuantity;
        this.todayPrice = todayPrice;
    }
}
