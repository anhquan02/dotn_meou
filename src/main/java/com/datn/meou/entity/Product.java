package com.datn.meou.entity;


import com.datn.meou.model.ProductDTO;
import com.datn.meou.model.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "dotn_product")
public class Product extends BaseEntity {

    private String name;
    private String description;
    private Integer status;

}
