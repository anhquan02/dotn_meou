package com.datn.meou.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.datn.meou.entity.Sole;
import com.datn.meou.repository.SoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SoleService {
    private final SoleRepository soleRepository;

    public Sole saveSole(String name) {
        Sole findSoleByName = this.findByName(name);
        if (findSoleByName != null) {
            return findSoleByName;
        }
        Sole sole = Sole.builder().name(name).build();
        return soleRepository.save(sole);
    }

    public List<Sole> findAllSoles() {
        return this.soleRepository.findAll();
    }

    public Sole findById(Long id) {
        return this.soleRepository.findById(id).orElse(null);
    }

    public Sole findByName(String name) {
        return this.soleRepository.findByName(name);
    }

    public void deleteSole(Long id) {
        Sole sole = this.findById(id);
        if (sole != null) {
            sole.setDeleted(!sole.getDeleted());
            this.soleRepository.save(sole);
        }
    }
}
