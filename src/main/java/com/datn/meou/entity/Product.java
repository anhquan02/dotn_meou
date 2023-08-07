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

@SqlResultSetMapping(
        name = "getProductByName",
        classes = {
                @ConstructorResult(
                        targetClass = ProductDTO.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "quantity", type = Integer.class),
                                @ColumnResult(name = "price", type = BigDecimal.class),
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "nameSole", type = String.class),
                                @ColumnResult(name = "nameInsole", type = String.class),
                                @ColumnResult(name = "nameSize", type = String.class),
                                @ColumnResult(name = "nameColor", type = String.class)

                        }
                )
        }
)
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
