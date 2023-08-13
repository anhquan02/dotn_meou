package com.datn.meou.services;

import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.ImageDTO;
import org.springframework.stereotype.Service;

import com.datn.meou.entity.Image;
import com.datn.meou.repository.ImageRepository;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageService {
    
    private final ImageRepository imageRepository;

    public Image saveImage(String name) {
        Image findImageByName = this.findByName(name);
        if (findImageByName != null) {
            return findImageByName;
        }
        Image image = Image.builder().name(name).build();
        return imageRepository.save(image);
    }

    public List<Image> saveListImage(List<ImageDTO> imageDTOList) {
        if(imageDTOList != null){
            List<Image> imageList = new ArrayList<>();
            for (ImageDTO dto : imageDTOList){
                Image image = Image
                        .builder()
                        .name(dto.getName())
                        .productId(dto.getProductId())
                        .build();
                imageList.add(image);
            }
            return imageRepository.saveAll(imageList);
        }
        throw new BadRequestException("Lưu ảnh thất bại");
    }

    public Image findByName(String name) {
        return imageRepository.findByName(name);
    }

    public Image findById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }
}
