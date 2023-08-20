package com.datn.meou.entity;


import com.datn.meou.model.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@SqlResultSetMapping(
        name = "advancedSearchProduct",
        classes = {
                @ConstructorResult(
                        targetClass = ProductDTO.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "image", type = String.class),
                                @ColumnResult(name = "minPrice", type = BigDecimal.class),
                                @ColumnResult(name = "maxPrice", type = BigDecimal.class),
                                @ColumnResult(name = "quantity", type = BigDecimal.class)

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
    private String image;
}
