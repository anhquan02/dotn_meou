package com.datn.meou.services;

import org.springframework.stereotype.Service;

import com.datn.meou.entity.ProductItem;
import com.datn.meou.repository.ProductItemRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductItemSerivce {
    private final ProductItemRepository productItemRepository;
    private final SoleService soleService;
    private final BrandService brandService;
    private final ImageService imageService;
    private final InsoleSerivce insoleSerivce;
    private final ColorService colorService;

}
