package com.datn.meou.services;

import com.datn.meou.entity.Image;
import com.datn.meou.model.StatisticalDTOS;
import com.datn.meou.repository.ImageRepository;
import com.datn.meou.repository.ProductItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatisticalService {
    private final ProductItemRepository productItemRepository;

    private final ImageRepository imageRepository;

    public List<StatisticalDTOS> topSales(){
        List<StatisticalDTOS> item = productItemRepository.topSales();
        for (StatisticalDTOS dto : item ){
            Image image = imageRepository.findFirstByProductItemId(dto.getProductItemId());
            dto.setImage(image.getName());
        }
        return item;
    }


}
