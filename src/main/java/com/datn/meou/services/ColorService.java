package com.datn.meou.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.datn.meou.entity.Color;
import com.datn.meou.repository.ColorRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ColorService {
    private final ColorRepository colorRepository;

    public Color saveColor(String name) {
        Color findColorByName = this.findByName(name);
        if (findColorByName != null) {
            return findColorByName;
        }
        Color color = Color.builder().name(name).build();
        return colorRepository.save(color);
    }

    public List<Color> findAllColors() {
        return colorRepository.findAll();
    }

    public Color findByName(String name) {
        return colorRepository.findByName(name);
    }

    public Color findById(Long id) {
        return colorRepository.findById(id).orElse(null);
    }

    public void deleteColor(Long id) {
        Color color = this.findById(id);
        if (color != null) {
            color.setDeleted(!color.getDeleted());
            colorRepository.save(color);
        }
    }
}
