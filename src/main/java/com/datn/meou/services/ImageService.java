package com.datn.meou.services;

import org.springframework.stereotype.Service;

import com.datn.meou.entity.Image;
import com.datn.meou.repository.ImageRepository;

import lombok.AllArgsConstructor;

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

    public Image findByName(String name) {
        return imageRepository.findByName(name);
    }

    public Image findById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }
}
