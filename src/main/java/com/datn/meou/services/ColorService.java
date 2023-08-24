package com.datn.meou.services;

import java.util.List;
import java.util.Optional;

import com.datn.meou.entity.Size;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.ColorDTO;
import com.datn.meou.model.SizeDTO;
import com.datn.meou.util.DataUtil;
import com.datn.meou.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.datn.meou.entity.Brand;
import com.datn.meou.entity.Color;
import com.datn.meou.repository.ColorRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ColorService {
    private final ColorRepository colorRepository;

    @Autowired
    MapperUtil mapperUtil;

    public Color saveColor(ColorDTO dto) {
        Color color = Color
                .builder()
                .name(dto.getName())
                .build();
        color.setStatus(true);
        return colorRepository.save(color);
    }

    public Color updateColor(ColorDTO dto) {
        Optional<Color> colorOptional = this.colorRepository.findByIdAndStatus(dto.getId(), true);
        if (colorOptional.isPresent()) {
            Color color = MapperUtil.map(dto, Color.class);
            color.setStatus(true);
            this.colorRepository.save(color);
            return color;
        }
        throw new BadRequestException("Không có màu sắc này");
    }

    public List<Color> findAllColorList() {
        return colorRepository.findAllByStatus(true);
    }

    public Page<Color> findAllColorPage(Pageable pageable) {
        return colorRepository.findAllByStatus(true, pageable);
    }


    public Page<Color> findByNameContaining(String name, Pageable pageable) {
        if (!DataUtil.isNullObject(name)) {
            return this.colorRepository.findByStatusAndNameContaining(true, name, pageable);
        }
        return this.colorRepository.findAllByStatus(true, pageable);
    }

    public Color findById(Long id) {
        Optional<Color> color = this.colorRepository.findByIdAndStatus(id, true);
        if (color.isPresent()) {
            return color.get();
        }
        throw new BadRequestException("Không tìm thấy màu sắc này");
    }

    public void deleteColor(List<Long> ids) {
        if (ids.size() > 0) {
            for (Long id : ids) {
                Color color = findById(id);
                color.setStatus(false);
                this.colorRepository.save(color);
            }
        }
    }

    public List<Color> getAllColorByProductId(Long productId) {
        return this.colorRepository.getAllColorByProductId(productId);
    }
}
