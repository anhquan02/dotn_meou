package com.datn.meou.services;

import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.ImageDTO;
import com.datn.meou.util.DataUtil;
import com.datn.meou.util.MapperUtil;
import org.hibernate.mapping.Index;
import org.springframework.stereotype.Service;

import com.datn.meou.entity.Image;
import com.datn.meou.repository.ImageRepository;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Image> saveListImage(List<ImageDTO> imageDTOList, Long productItemID) {
        if(imageDTOList != null){
            List<Image> imageList = new ArrayList<>();
            for (ImageDTO dto : imageDTOList){
                Image image = Image
                        .builder()
                        .name(dto.getName())
                        .productItemId(productItemID)
                        .build();
                imageList.add(image);
            }
            return imageRepository.saveAll(imageList);
        }
        throw new BadRequestException("Lưu ảnh thất bại");
    }

    public List<Image> updateImage(List<ImageDTO> imageDTOList, Long productItemID) {
            for (ImageDTO dto : imageDTOList){
                Optional<Image> image = imageRepository.findById(dto.getId());
                if((image == null || image.isEmpty()) && dto.getName() == null ){
                    throw new BadRequestException("Ảnh không tồn tại để xóa");
                }
                //Xóa ảnh
                if(image.isPresent() || dto.getName() == null || !image.isEmpty()){
                    imageRepository.delete(image.get());
                }
                //thêm ảnh
                if(image == null || image.isEmpty()){
                    Image imageSave = Image
                            .builder()
                            .name(dto.getName())
                            .productItemId(productItemID)
                            .build();
                    imageRepository.save(imageSave);
                }


            }
            return imageRepository.findAllByProductItemId(productItemID);
    }

    public Image findByName(String name) {
        return imageRepository.findByName(name);
    }

    public Image findById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    public List<ImageDTO> findAllByProductItemId(Long id){
        return MapperUtil.mapList(imageRepository.findAllByProductItemId(id), ImageDTO.class);
    }
}
