package com.datn.meou.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.datn.meou.entity.Size;
import com.datn.meou.repository.SizeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SizeService {
    private final SizeRepository sizeRepository;
    
    public Size saveSize(String name) {
        Size findSizeByName = this.findByName(name);
        if (findSizeByName != null) {
            return findSizeByName;
        }
        Size size = Size.builder().name(name).build();
        return sizeRepository.save(size);
    }

    public Size saveSize(Size size) {
        return sizeRepository.save(size);
    }

    public List<Size> findAllSizes() {
        return sizeRepository.findAll();
    }

    public Size findByName(String name) {
        return sizeRepository.findByName(name);
    }

    public Size findById(Long id) {
        return sizeRepository.findById(id).orElse(null);
    }

    public void deleteSize(Long id) {
        Size size = this.findById(id);
        if (size != null) {
            size.setDeleted(!size.getDeleted());
            sizeRepository.save(size);
        }
    }
}
