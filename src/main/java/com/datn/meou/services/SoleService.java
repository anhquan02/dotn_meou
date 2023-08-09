package com.datn.meou.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.datn.meou.entity.Size;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.SizeDTO;
import com.datn.meou.model.SoleDTO;
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

    public Sole addOrUpdate(SoleDTO dto){
        Date date = new Date();
        if(dto.getId() == null){
            return null;
        }
        Sole sole = soleRepository.findById(dto.getId()).orElse(null);

        if(sole == null){
            Sole sole1 = new Sole();
            sole1.setName(dto.getName());
            sole1.setStatus(false);
            sole1.setCreatedDate(date);
            sole1.setUpdatedDate(date);
            return soleRepository.save(sole1);
        }else{
            sole.setName(dto.getName());
            sole.setUpdatedDate(date);
            return soleRepository.save(sole);
        }
    }

    public Sole deteleSole(Long id){
        Date date = new Date();
        if(id == null){
            throw new BadRequestException("Xóa thất bại");
        }
        Sole sole = soleRepository.findById(id).orElse(null);
        sole.setStatus(true);
        sole.setUpdatedDate(date);
        return soleRepository.save(sole);
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
}
