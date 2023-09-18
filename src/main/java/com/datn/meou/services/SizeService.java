package com.datn.meou.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.datn.meou.entity.Brand;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.BrandDTO;
import com.datn.meou.model.SizeDTO;
import com.datn.meou.util.DataUtil;
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
public class SizeService {

    private final SizeRepository sizeRepository;

    @Autowired
    MapperUtil mapperUtil;

    public Size saveSize(SizeDTO dto) {
        List<Size> checkSize = sizeRepository.findAllByName(dto.getName());
        if(!checkSize.isEmpty()){
            throw new BadRequestException("Size này đã tồn tại");
        }
        Size size = Size
                .builder()
                .name(dto.getName())
                .build();
        size.setStatus(true);
        return sizeRepository.save(size);
    }

    public Size updateSize(SizeDTO dto) {
        Optional<Size> sizeOptional = this.sizeRepository.findByIdAndStatus(dto.getId(), true);
        if (sizeOptional.isPresent()) {
            Size size = MapperUtil.map(dto, Size.class);
            size.setStatus(true);
            this.sizeRepository.save(size);
            return size;
        }
        throw new BadRequestException("Không có size này");
    }

    public List<Size> findAllBrandList() {
        return sizeRepository.findAllByStatus(true);
    }

    public Page<Size> findAllBrandPage(Pageable pageable) {
        return sizeRepository.findAllByStatus(true, pageable);
    }


    public Page<Size> findByNameContaining(String name, Pageable pageable) {
        if (!DataUtil.isNullObject(name)) {
            return this.sizeRepository.findByStatusAndNameContaining(true, name, pageable);
        }
        return this.sizeRepository.findAllByStatus(true, pageable);
    }

    public Size findById(Long id) {
        Optional<Size> size = this.sizeRepository.findByIdAndStatus(id, true);
        if (size.isPresent()) {
            return size.get();
        }
        throw new BadRequestException("Không tìm thấy size này");
    }

    public void deleteSize(List<Long> ids) {
        if (ids.size() > 0) {
            for (Long id : ids) {
                Size size = findById(id);
                size.setStatus(false);
                this.sizeRepository.save(size);
            }
        }
    }

    public List<Size> getAllSizeByProductId(Long productId) {
        return this.sizeRepository.getAllSizeByProductId(productId);
    }

}
