package com.datn.meou.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.datn.meou.entity.Size;
import com.datn.meou.entity.Sole;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.InsoleDTO;
import com.datn.meou.model.SizeDTO;
import com.datn.meou.model.SoleDTO;
import com.datn.meou.util.DataUtil;
import com.datn.meou.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    MapperUtil mapperUtil;

    public Insole saveInsole(InsoleDTO dto) {
        Insole insole = Insole
                .builder()
                .name(dto.getName())
                .build();
        insole.setStatus(true);
        return insoleRepository.save(insole);
    }

    public Insole updateInsole(InsoleDTO dto) {
        Optional<Insole> insoleOptional = this.insoleRepository.findByIdAndStatus(dto.getId(), true);
        if (insoleOptional.isPresent()) {
            Insole insole = MapperUtil.map(dto, Insole.class);
            insole.setStatus(true);
            this.insoleRepository.save(insole);
            return insole;
        }
        throw new BadRequestException("Không có lót giày này");
    }

    public List<Insole> findAllInsoleList() {
        return insoleRepository.findAllByStatus(true);
    }

    public Page<Insole> findAllInsolePage(Pageable pageable) {
        return insoleRepository.findAllByStatus(true, pageable);
    }


    public Page<Insole> findByNameContaining(String name, Pageable pageable) {
        if (!DataUtil.isNullObject(name)) {
            return this.insoleRepository.findByStatusAndNameContaining(true, name, pageable);
        }
        return this.insoleRepository.findAllByStatus(true, pageable);
    }

    public Insole findById(Long id) {
        Optional<Insole> insole = this.insoleRepository.findByIdAndStatus(id, true);
        if (insole.isPresent()) {
            return insole.get();
        }
        throw new BadRequestException("Không tìm thấy lót giày này");
    }

    public void deleteInsole(List<Long> ids) {
        if (ids.size() > 0) {
            for (Long id : ids) {
                Insole insole = findById(id);
                insole.setStatus(false);
                this.insoleRepository.save(insole);
            }
        }
    }
}
