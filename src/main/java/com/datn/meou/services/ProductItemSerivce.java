package com.datn.meou.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.datn.meou.entity.Brand;
import com.datn.meou.entity.Color;
import com.datn.meou.entity.Sole;
import com.datn.meou.entity.Insole;
import com.datn.meou.entity.Product;
import com.datn.meou.entity.Size;
import com.datn.meou.entity.ProductItem;
import com.datn.meou.repository.ProductItemRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductItemSerivce {
    private final ProductItemRepository productItemRepository;
    private final ProductService productService;
    private final SoleService soleService;
    private final BrandService brandService;
    private final ImageService imageService;
    private final InsoleSerivce insoleSerivce;
    private final ColorService colorService;
    private final SizeService sizeService;

    public ProductItem saveProductItem(ProductItem productItem) {
//        productItem.setDeleted(false);
        productItem.setStatus(0);
        return productItemRepository.save(productItem);
    }

    public List<Brand> findAllBrands() {
        return brandService.findAllBrands();
    }

    public List<Product> findAllProducts() {
        return productService.findAllProducts();
    }

    public List<Sole> findAllSoles() {
        return soleService.findAllSoles();
    }

    public List<Insole> findAllInsoles() {
        return insoleSerivce.findAllInsoles();
    }

    public List<Color> findAllColors() {
        return colorService.findAllColors();
    }

    public List<Size> findAllSizes() {
        return sizeService.findAllSizes();
    }

    public Page<ProductItem> findAll(Pageable pageable) {
        return productItemRepository.findAll(pageable);
    }

    public ProductItem findById(Long id) {
        return productItemRepository.findById(id).orElse(null);
    }

    public Page<ProductItem> findByNameContaining(String name, Pageable pageable) {
        List<ProductItem> productItems = productItemRepository.findByNameContaining(name);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProductItem> subList;
        if (productItems.size() < startItem) {
            return Page.empty();
        } else {
            int toIndex = Math.min(startItem + pageSize, productItems.size());
            subList = productItems.subList(startItem, toIndex);
        }
        Page<ProductItem> productItemPage = new PageImpl<ProductItem>(subList,
                org.springframework.data.domain.PageRequest.of(currentPage, pageSize), productItems.size());
        return productItemPage;
    }

    public Page<ProductItem> findFilter(String name, String brandId, String soleId, String insoleId, String colorId,
            String sizeId, String status, Pageable pageable) {
        List<ProductItem> productItems = productItemRepository.findFilter(name, brandId, soleId, insoleId, colorId,
                sizeId, status);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProductItem> subList;
        if (productItems.size() < startItem) {
            return Page.empty();
        } else {
            int toIndex = Math.min(startItem + pageSize, productItems.size());
            subList = productItems.subList(startItem, toIndex);
        }
        Page<ProductItem> productItemPage = new PageImpl<ProductItem>(subList,
                org.springframework.data.domain.PageRequest.of(currentPage, pageSize), productItems.size());
        return productItemPage;
    }

}
