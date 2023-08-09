package com.datn.meou.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.datn.meou.entity.Size;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.InsoleDTO;
import com.datn.meou.model.SizeDTO;
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

    public Insole addOrUpdate(InsoleDTO dto){
        Date date = new Date();
        validate(dto);
        Optional<Insole> insole = insoleRepository.findById(dto.getId());

        if(insole.isEmpty()){
            Insole insole1 = new Insole();
            insole1.setName(dto.getName());
            insole1.setStatus(false);
            insole1.setCreatedDate(date);
            insole1.setUpdatedDate(date);
            return insoleRepository.save(insole1);
        }else{
            insole.get().setName(dto.getName());
            insole.get().setUpdatedDate(date);
            return insoleRepository.save(insole.get());
        }
    }

    public Insole deteleInsole(Long id){
        Date date = new Date();
        if(id == null){
            throw new BadRequestException("Xóa thất bại");
        }
        Insole size = insoleRepository.findById(id).orElse(null);
        size.setStatus(true);
        return insoleRepository.save(size);
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
        Page<Insole> sizePage = new PageImpl<Insole>(list, PageRequest.of(currentPage, pageSize), insoles.size());
        return sizePage;
    }

    public Insole findByName(String name) {
        return insoleRepository.findByName(name);
    }

    public void validate(InsoleDTO dto){
        if(dto.getId() == null){
            throw new BadRequestException("Bad Request");
        }

        if(dto.getName() == null){
            throw new BadRequestException("Name không được để trống");
        }

        if(dto.getName().length() != 2){
            throw new BadRequestException("Name sai định dạng");
        }
    }
}
