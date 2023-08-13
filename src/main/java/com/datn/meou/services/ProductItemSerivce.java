package com.datn.meou.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.datn.meou.entity.*;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.*;
import com.datn.meou.util.DataUtil;
import com.datn.meou.util.MapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public List<ProductItem> saveProductItem(ProductItemDTOS dtos) {
        if(dtos.getDto() != null){
            List<ProductItem> productItemList = new ArrayList<>();
            for(ProductItemDTO item : dtos.getDto()){
                ProductItem productItemCheck =
                        productItemRepository.findByProductIdAndColorIdAndInsoleIdAndSizeIdAndSoleId(item.getProductId(), item.getColorId(), item.getInsoleId(), item.getSizeId(), item.getSoleId());
                if(productItemCheck == null){
                    ProductItem productItem = ProductItem
                            .builder()
                            .productId(item.getProductId())
                            .name(item.getName())
                            .colorId(item.getColorId())
                            .sizeId(item.getSizeId())
                            .soleId(item.getSoleId())
                            .insoleId(item.getInsoleId())
                            .quantity(item.getQuantity())
                            .image(item.getNameImage())
                            .price(item.getPrice())
                            .build();
                    productItem.setStatus(true);
                    ProductItem productItem1 = productItemRepository.save(productItem);
                    imageService.saveListImage(item.getImageList());
                    productItemList.add(productItem1);

                }else{
                    productItemCheck.setQuantity(item.getQuantity() + productItemCheck.getQuantity());
                    ProductItem productItem1 = productItemRepository.save(productItemCheck);
                    productItemList.add(productItemCheck);
                }

            }
            return productItemList;
        }
        throw new BadRequestException("Lưu sản phẩm thất bại");
    }

    public List<ProductItem>  updateProductItem(ProductItemDTOS dtos) {
        if(dtos.getDto() != null){
            List<ProductItem> productItemList = new ArrayList<>();
            for(ProductItemDTO item : dtos.getDto()){
                Optional<ProductItem> productItem = productItemRepository.findById(item.getId());
                productItem.get().setQuantity(item.getQuantity());
                productItem.get().setName(item.getName());
                productItem.get().setColorId(item.getColorId());
                productItem.get().setInsoleId(item.getInsoleId());
                productItem.get().setSizeId(item.getSizeId());
                productItem.get().setSoleId(item.getSoleId());
                productItem.get().setImage(item.getNameImage());
                productItem.get().setPrice(item.getPrice());
                ProductItem productItem1 = productItemRepository.save(productItem.get());
                imageService.saveListImage(item.getImageList());
                productItemList.add(productItem1);
            }
            return productItemList;
        }

        throw new BadRequestException("Cập nhật sản phẩm thất bại");
    }

    public List<ProductItem> findAllProductItemList() {
        return productItemRepository.findAllByStatus(true);
    }

    public Page<ProductItem> findAllProductItemPage(Pageable pageable) {
        return productItemRepository.findAllByStatus(true, pageable);
    }


    public Page<ProductItem> findByNameContaining(String name, Pageable pageable) {
        if (!DataUtil.isNullObject(name)) {
            return this.productItemRepository.findByStatusAndNameContaining(true, name, pageable);
        }
        return this.productItemRepository.findAllByStatus(true, pageable);
    }

    public ProductItem findById(Long id) {
        Optional<ProductItem> productItem = this.productItemRepository.findByIdAndStatus(id, true);
        if (productItem.isPresent()) {
            return productItem.get();
        }
        throw new BadRequestException("Không tìm thấy size này");
    }

    public void deleteProductItem(List<Long> ids) {
        if (ids.size() > 0) {
            for (Long id : ids) {
                ProductItem size = findById(id);
                size.setStatus(false);
                this.productItemRepository.save(size);
            }
        }
    }

    public List<ProductItem> findProductItemByProjectId(Long projectId){
        return productItemRepository.findAllByProductIdAndStatus(projectId, true);
    }




}
