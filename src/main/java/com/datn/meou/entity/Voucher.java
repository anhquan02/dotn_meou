package com.datn.meou.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "dotn_voucher")
public class Voucher extends BaseEntity {
    private String name;
    private LocalDateTime dayStart;
    private LocalDateTime dayEnd;
    private BigDecimal value;
    private Boolean status;
}
