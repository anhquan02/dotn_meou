package com.datn.meou.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "dotn_voucher")
public class Voucher extends BaseEntity{
    private String name;
    private LocalDate dayStart;
    private LocalDate dayEnd;;
    private Integer quantity;
    private Double value;
}
