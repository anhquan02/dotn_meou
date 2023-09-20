package com.datn.meou.services;

import com.datn.meou.entity.Brand;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.BrandDTO;
import com.datn.meou.repository.BrandRepository;
import com.datn.meou.util.DataUtil;
import com.datn.meou.util.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public Brand saveBrand(BrandDTO dto) {
        Brand brand = Brand
                .builder()
                .name(dto.getName())
                .build();
        brand.setStatus(true);
        return brandRepository.save(brand);
    }

    public Brand updateBrand(BrandDTO dto) {
        Optional<Brand> brandOptional = this.brandRepository.findByIdAndStatus(dto.getId(), true);
        if (brandOptional.isPresent()) {
            Brand brand = MapperUtil.map(dto, Brand.class);
            brand.setStatus(true);
            this.brandRepository.save(brand);
            return brand;
        }
        throw new BadRequestException("Không có thương hiệu này !");
    }

    public List<Brand> findAllBrandList() {
        return brandRepository.findAllByStatus(true);
    }

    public Page<Brand> findAllBrandPage(Pageable pageable) {
        return brandRepository.findAllByStatus(true, pageable);
    }


    public Page<Brand> findByNameContaining(String name, Pageable pageable) {
        if (!DataUtil.isNullObject(name)) {
            return this.brandRepository.findByStatusAndNameContaining(true, name, pageable);
        }
        return this.brandRepository.findAllByStatus(true, pageable);
    }

    public Brand findById(Long id) {
        Optional<Brand> brand = this.brandRepository.findByIdAndStatus(id, true);
        if (brand.isPresent()) {
            return brand.get();
        }
        throw new BadRequestException("Không tìm thấy thương hiệu này");
    }

    public void deleteBrand(List<Long> ids) {
        if (ids.size() > 0) {
            for (Long id : ids) {
                Brand brand = findById(id);
                brand.setStatus(false);
                this.brandRepository.save(brand);
            }
        }
    }

}
