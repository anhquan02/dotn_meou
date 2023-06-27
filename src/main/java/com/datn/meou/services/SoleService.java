package com.datn.meou.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Sole saveSole(Sole sole) {
        return soleRepository.save(sole);
    }

    public List<Sole> findAllSoles() {
        return this.soleRepository.findAll();
    }

    public Page<Sole> findByNameContaining(String name, Pageable pageable) {
        List<Sole> soles = soleRepository.findByNameContaining(name);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Sole> list;
        if (soles.size() < startItem) {
            return Page.empty();
        } else {
            int toIndex = Math.min(startItem + pageSize, soles.size());
            list = soles.subList(startItem, toIndex);
        }
        Page<Sole> solePage = new PageImpl<Sole>(list, PageRequest.of(currentPage, pageSize), soles.size());
        return solePage;
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
