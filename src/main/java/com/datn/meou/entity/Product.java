package com.datn.meou.entity;


import com.datn.meou.model.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@SqlResultSetMapping(
        name = "advancedSearchProduct",
        classes = {
                @ConstructorResult(
                        targetClass = ProductDTO.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "price", type = BigDecimal.class),
                                @ColumnResult(name = "image", type = String.class),

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
    private Boolean status;
    private String image;

    private Long brandId;
    private BigDecimal price;

}
