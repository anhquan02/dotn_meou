package com.datn.meou.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductDTO extends BaseModel {

    @NotEmpty(message = "Tên không được rỗng")
    @Size(min = 2, max = 500, message = "Tên phải từ 2 đến 500 ký tự")
    private String name;

    private String description;
    private Integer status;
    private BigDecimal price;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal quantity;
    private Long productId;
    private Long soleId;
    private Long colorId;
    private Long insoleId;
    private Long brandId;
    private Long sizeId;
    private Long imgId;

    private String image;
    private String nameSize;
    private String nameImage;
    private String nameSole;
    private String nameColor;
    private String nameInsole;

    public ProductDTO(Long id, String name, String image, BigDecimal minPrice, BigDecimal maxPrice, BigDecimal quantity, Integer status) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.status = status;
    }
}
