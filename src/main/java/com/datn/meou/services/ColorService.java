package com.datn.meou.services;

import java.util.List;

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

    public Color saveColor(String name) {
        Color findColorByName = this.findByName(name);
        if (findColorByName != null) {
            return findColorByName;
        }
        Color color = Color.builder().name(name).build();
        return colorRepository.save(color);
    }

    public Color saveColor(Color color) {
        return colorRepository.save(color);
    }

    public List<Color> findAllColors() {
        return colorRepository.findAll();
    }

    public Page<Color> findByNameContaining(String name, Pageable pageable) {
        List<Color> colors = colorRepository.findByNameContaining(name);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Color> list;
        if (colors.size() < startItem) {
            return Page.empty();
        } else {
            int toIndex = Math.min(startItem + pageSize, colors.size());
            list = colors.subList(startItem, toIndex);
        }
        Page<Color> colorPage = new PageImpl<Color>(list, PageRequest.of(currentPage, pageSize), colors.size());
        return colorPage;
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
