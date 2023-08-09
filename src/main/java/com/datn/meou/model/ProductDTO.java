package com.datn.meou.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductDTO extends BaseModel {

    private String name;
    private String description;
    private Integer status;
    private BigDecimal price;
    private Integer quantity;
    private Long productId;
    private Long soleId;
    private Long colorId;
    private Long insoleId;
    private Long brandId;
    private Long sizeId;
    private Long imgId;
    private String nameSize;
    private String nameImage;
    private String nameSole;
    private String nameColor;
    private String nameInsole;
}
