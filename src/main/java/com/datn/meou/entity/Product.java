package com.datn.meou.entity;


import com.datn.meou.model.ProductDTO;
import com.datn.meou.model.StatisticalDTO;
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
                                @ColumnResult(name = "brandId", type = Long.class),
                                @ColumnResult(name = "minPrice", type = BigDecimal.class),
                                @ColumnResult(name = "maxPrice", type = BigDecimal.class),
                                @ColumnResult(name = "quantity", type = BigDecimal.class),
                                @ColumnResult(name = "status", type = Integer.class)

                        }
                )
        }
)

@SqlResultSetMapping(
        name = "payOnline",
        classes = {
                @ConstructorResult(
                        targetClass = ProductDTO.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "image", type = String.class),
                                @ColumnResult(name = "minPrice", type = BigDecimal.class),
                                @ColumnResult(name = "maxPrice", type = BigDecimal.class),
                                @ColumnResult(name = "status", type = Integer.class),
                                @ColumnResult(name = "description", type = String.class),
                                @ColumnResult(name = "brandId", type = Long.class),
                                @ColumnResult(name = "nameBrand", type = String.class)

                        }
                )
        }
)
@SqlResultSetMapping(
        name = "statisticalMonthly",
        classes = {
                @ConstructorResult(
                        targetClass = StatisticalDTO.class,
                        columns = {
                                @ColumnResult(name = "monthQuantity", type = Integer.class),
                                @ColumnResult(name = "monthPrice", type = Double.class),
                                @ColumnResult(name = "quantityOrder", type = BigDecimal.class),
                        }
                )
        }
)

@SqlResultSetMapping(
        name = "statisticalToday",
        classes = {
                @ConstructorResult(
                        targetClass = StatisticalDTO.class,
                        columns = {
                                @ColumnResult(name = "todayQuantity", type = Integer.class),
                                @ColumnResult(name = "todayPrice", type = Double.class),
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
    private Long brandId;
}
