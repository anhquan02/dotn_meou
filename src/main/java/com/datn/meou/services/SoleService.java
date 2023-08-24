package com.datn.meou.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.datn.meou.entity.Size;
import com.datn.meou.exception.BadRequestException;
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

import com.datn.meou.entity.Sole;
import com.datn.meou.repository.SoleRepository;

import lombok.AllArgsConstructor;

import javax.validation.constraints.Null;

@Service
@AllArgsConstructor
public class SoleService {
    private final SoleRepository soleRepository;


    @Autowired
    MapperUtil mapperUtil;

    public Sole saveSole(SoleDTO dto) {
        Sole sole = Sole
                .builder()
                .name(dto.getName())
                .build();
        sole.setStatus(true);
        return soleRepository.save(sole);
    }

    public Sole updateSole(SoleDTO dto) {
        Optional<Sole> soleOptional = this.soleRepository.findByIdAndStatus(dto.getId(), true);
        if (soleOptional.isPresent()) {
            Sole sole = MapperUtil.map(dto, Sole.class);
            sole.setStatus(true);
            this.soleRepository.save(sole);
            return sole;
        }
        throw new BadRequestException("Không có đế giày này");
    }

    public List<Sole> findAllSoleList() {
        return soleRepository.findAllByStatus(true);
    }

    public Page<Sole> findAllSolePage(Pageable pageable) {
        return soleRepository.findAllByStatus(true, pageable);
    }


    public Page<Sole> findByNameContaining(String name, Pageable pageable) {
        if (!DataUtil.isNullObject(name)) {
            return this.soleRepository.findByStatusAndNameContaining(true, name, pageable);
        }
        return this.soleRepository.findAllByStatus(true, pageable);
    }

    public Sole findById(Long id) {
        Optional<Sole> sole = this.soleRepository.findByIdAndStatus(id, true);
        if (sole.isPresent()) {
            return sole.get();
        }
        throw new BadRequestException("Không tìm thấy đế giày này");
    }

    public void deleteSole(List<Long> ids) {
        if (ids.size() > 0) {
            for (Long id : ids) {
                Sole sole = findById(id);
                sole.setStatus(false);
                this.soleRepository.save(sole);
            }
        }
    }

    public List<Sole> getAllSoleByProductId(Long productId) {
        return this.soleRepository.getAllSoleByProductId(productId);
    }
}
