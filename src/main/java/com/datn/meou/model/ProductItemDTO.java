package com.datn.meou.model;

import com.datn.meou.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.Message;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private String nameBrand;
    private List<ImageDTO> imageList = new ArrayList<>();

    private List<Image> images = new ArrayList<>();

    private BigDecimal price;

    public ProductItemDTO(Long id, Integer quantity, BigDecimal price,
                          String name, String nameSole, String nameInsole,
                          String nameSize, String nameColor, String nameBrand,
                          Long soleId, Long insoleId, Long sizeId, Long colorId, Long brandId, Integer status) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.nameSole = nameSole;
        this.nameInsole = nameInsole;
        this.nameSize = nameSize;
        this.nameColor = nameColor;
        this.nameBrand = nameBrand;
        this.soleId = soleId;
        this.insoleId = insoleId;
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.brandId = brandId;
        this.status = status;

    }
}
