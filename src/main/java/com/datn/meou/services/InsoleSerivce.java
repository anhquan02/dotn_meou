package com.datn.meou.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.datn.meou.entity.Insole;
import com.datn.meou.repository.InsoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InsoleSerivce {
    private final InsoleRepository insoleRepository;

    public Insole saveInsole(String name) {
        Insole findInsoleByName = this.findByName(name);
        if (findInsoleByName != null) {
            return findInsoleByName;
        }
        Insole insole = Insole.builder().name(name).build();
        return insoleRepository.save(insole);
    }

    public Insole saveInsole(Insole insole) {
        return insoleRepository.save(insole);
    }

    public List<Insole> findAllInsoles() {
        return insoleRepository.findAll();
    }

    public Page<Insole> findByNameContaining(String name, Pageable pageable) {
        List<Insole> insoles = insoleRepository.findByNameContaining(name);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Insole> list;
        if (insoles.size() < startItem) {
            return Page.empty();
        } else {
            int toIndex = Math.min(startItem + pageSize, insoles.size());
            list = insoles.subList(startItem, toIndex);
        }
        Page<Insole> insolePage = new PageImpl<Insole>(list, PageRequest.of(currentPage, pageSize), insoles.size());
        return insolePage;
    }

    public Insole findByName(String name) {
        return insoleRepository.findByName(name);
    }

    public Insole findById(Long id) {
        return insoleRepository.findById(id).orElse(null);
    }

    public void deleteInsole(Long id) {
        Insole insole = this.findById(id);
        if (insole != null) {
//            insole.setDeleted(!insole.getDeleted());
            insoleRepository.save(insole);
        }
    }
}
