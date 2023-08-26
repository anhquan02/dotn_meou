package com.datn.meou.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ImageDTOS {
    Long productItemID;
    List<ImageDTO> imageDTOList;
}
