package com.datn.meou.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.SizeDTO;
import com.datn.meou.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.datn.meou.entity.Size;
import com.datn.meou.repository.SizeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SizeService {private final SizeRepository sizeRepository;

    @Autowired
    MapperUtil mapperUtil;

    public Size addOrUpdate(SizeDTO dto){
        Date date = new Date();
        validate(dto);
        Optional<Size> size = sizeRepository.findById(dto.getId());

        if(size.isEmpty()){
            Size size1 = new Size();
            size1 = mapperUtil.map(dto, Size.class);
            size1.setCreatedDate(date);
            size1.setUpdatedDate(date);
            size1.setStatus(false);
            return sizeRepository.save(size1);
        }else{
            size.get().setName(dto.getName());
            size.get().setUpdatedDate(date);
            return sizeRepository.save(size.get());
        }
    }

    public Size deteleSize(Long id){
        Date date = new Date();
        if(id == null){
            throw new BadRequestException("Xóa thất bại");
        }
        Size size = sizeRepository.findById(id).orElse(null);
        size.setStatus(true);
        return sizeRepository.save(size);
    }

    public Page<Size> findByNameContaining(String name, Pageable pageable) {
        List<Size> sizes = sizeRepository.findByNameContaining(name);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Size> list;
        if (sizes.size() < startItem) {
            return Page.empty();
        } else {
            int toIndex = Math.min(startItem + pageSize, sizes.size());
            list = sizes.subList(startItem, toIndex);
        }
        Page<Size> sizePage = new PageImpl<Size>(list, PageRequest.of(currentPage, pageSize), sizes.size());
        return sizePage;
    }

    public Size findByName(String name) {
        return sizeRepository.findByName(name);
    }

    public void validate(SizeDTO dto){
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
