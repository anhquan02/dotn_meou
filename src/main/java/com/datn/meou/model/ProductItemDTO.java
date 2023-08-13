package com.datn.meou.model;

import com.datn.meou.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductItemDTO extends BaseModel {
    private String name;
    private Long productId;
    private Long soleId;
    private Long colorId;
    private Long insoleId;
    private Long brandId;
    private Long sizeId;
    private Long imgId;
    private Integer status;
    private Integer quantity;
    private String nameSize;
    private String nameImage;
    private String nameSole;
    private String nameColor;
    private String nameInsole;
    private List<ImageDTO> imageList;

    private BigDecimal price;

    public ProductItemDTO(Long id, Integer quantity, BigDecimal price, String name, String nameSole, String nameInsole, String nameSize, String nameColor) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.nameSole = nameSole;
        this.nameInsole = nameInsole;
        this.nameSize = nameSize;
        this.nameColor = nameColor;
    }
}
