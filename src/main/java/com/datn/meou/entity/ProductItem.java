package com.datn.meou.entity;

import com.datn.meou.model.ProductDTO;
import com.datn.meou.model.ProductItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "getProductByName",
        classes = {
                @ConstructorResult(
                        targetClass = ProductItemDTO.class,
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
@Table(name = "dotn_product_item")
public class ProductItem extends BaseEntity {
    private String name;
    private Long productId;
    private Long soleId;
    private Long colorId;
    private Long insoleId;
    private Long sizeId;
    private Long brandId;
    private Integer status;
    private Integer quantity;
    private BigDecimal price;
}
