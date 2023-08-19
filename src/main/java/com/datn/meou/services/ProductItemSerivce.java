package com.datn.meou.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.datn.meou.entity.*;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.*;
import com.datn.meou.repository.ProductRepository;
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
                        productItemRepository.findByProductIdAndColorIdAndInsoleIdAndSizeIdAndSoleIdAndBrandId(item.getProductId(), item.getColorId(), item.getInsoleId(), item.getSizeId(), item.getSoleId(), item.getBrandId());
                if(DataUtil.isNullObject(item.getProductId())) {
                    throw new BadRequestException("id sản phẩm không được để trống");
                }

                if(DataUtil.isNullObject(item.getSizeId())) {
                    throw new BadRequestException("Size không để trống");
                }

                if(DataUtil.isNullObject(item.getInsoleId())) {
                    throw new BadRequestException("Lót giày không để trống");
                }

                if(DataUtil.isNullObject(item.getSoleId())) {
                    throw new BadRequestException("Đế giày không để trống");
                }

                if(DataUtil.isNullObject(item.getColorId())) {
                    throw new BadRequestException("Màu sắc không để trống");
                }

                if(DataUtil.isNullObject(item.getQuantity())) {
                    throw new BadRequestException("Số lượng không để trống");
                }
                if(item.getQuantity() < 0){
                    throw new BadRequestException("Số lượng không được nhỏ hơn 0");
                }
                if(DataUtil.isNullOrEmpty(item.getImageList())){
                    throw new BadRequestException("Ảnh không được để trống");
                }
                if(DataUtil.isNullObject(item.getBrandId())){
                    throw new BadRequestException("Thương hiệu không được để trống");
                }
                if(DataUtil.isNullObject(item.getPrice())){
                    throw new BadRequestException("Giá không được để trống");
                }

                Product product = productService.findById(item.getProductId());
                if(product == null){
                    throw new BadRequestException("Sản phẩm không tồn tại");
                }
                if(brandService.findById(item.getBrandId()) ==  null){
                    throw new BadRequestException("Thương hiệu không tồn tại");
                }
                if(colorService.findById(item.getColorId()) == null){
                    throw new BadRequestException("Màu sắc không tồn tại");
                }

                if(soleService.findById(item.getSoleId()) == null){
                    throw new BadRequestException("Đế giày không tồn tại");
                }

                if(insoleSerivce.findById(item.getInsoleId()) == null){
                    throw new BadRequestException("Lót giày không tồn tại");
                }

                if(sizeService.findById(item.getSizeId()) == null){
                    throw new BadRequestException("Size không tồn tại");
                }

                if(productItemCheck == null){
                    ProductItem productItem = ProductItem
                            .builder()
                            .productId(item.getProductId())
                            .name(item.getName())
                            .colorId(item.getColorId())
                            .sizeId(item.getSizeId())
                            .soleId(item.getSoleId())
                            .insoleId(item.getInsoleId())
                            .brandId(item.getBrandId())
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .build();
                    productItem.setStatus(true);
                    ProductItem productItem1 = productItemRepository.save(productItem);
                    imageService.saveListImage(item.getImageList(),productItem1.getId());
                    productItemList.add(productItem1);

                }else{
                    productItemCheck.setQuantity(item.getQuantity() + productItemCheck.getQuantity());
                    productItemRepository.save(productItemCheck);
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
                if(DataUtil.isNullObject(item.getProductId())) {
                    throw new BadRequestException("id sản phẩm không được để trống");
                }

                if(DataUtil.isNullObject(item.getSizeId())) {
                    throw new BadRequestException("Size không để trống");
                }

                if(DataUtil.isNullObject(item.getInsoleId())) {
                    throw new BadRequestException("Lót giày không để trống");
                }

                if(DataUtil.isNullObject(item.getSoleId())) {
                    throw new BadRequestException("Đế giày không để trống");
                }

                if(DataUtil.isNullObject(item.getColorId())) {
                    throw new BadRequestException("Màu sắc không để trống");
                }

                if(DataUtil.isNullObject(item.getQuantity())) {
                    throw new BadRequestException("Số lượng không để trống");
                }
                if(item.getQuantity() < 0){
                    throw new BadRequestException("Số lượng không được nhỏ hơn 0");
                }
                if(DataUtil.isNullOrEmpty(item.getImageList())){
                    throw new BadRequestException("Ảnh không được để trống");
                }
                if(DataUtil.isNullObject(item.getBrandId())){
                    throw new BadRequestException("Thương hiệu không được để trống");
                }
                if(DataUtil.isNullObject(item.getPrice())){
                    throw new BadRequestException("Giá không được để trống");
                }
                Optional<ProductItem> productItem = productItemRepository.findById(item.getId());
                ProductItem productItem1 = productItem.get();
                if(productItem1 == null){
                    throw new BadRequestException("sản phẩm không tồn tại");
                }
                if(!DataUtil.isNullObject(item.getBrandId())){
                    if(brandService.findById(item.getBrandId()) == null){
                        throw new BadRequestException("Thương hiệu không tồn tại");
                    }
                }
                if(!DataUtil.isNullObject(item.getSoleId())){
                    if(soleService.findById(item.getSoleId()) == null){
                        throw new BadRequestException("Đế giày không tồn tại");
                    }
                }
                if(!DataUtil.isNullObject(item.getInsoleId())){
                    if(insoleSerivce.findById(item.getInsoleId()) == null){
                        throw new BadRequestException("Lót giày không tồn tại");
                    }
                }
                if(!DataUtil.isNullObject(item.getSizeId())){
                    if(sizeService.findById(item.getSizeId()) == null){
                        throw new BadRequestException("Size không tồn tại");
                    }
                }
                productItem1.setQuantity(item.getQuantity());
                productItem1.setName(item.getName());
                productItem1.setColorId(item.getColorId());
                productItem1.setInsoleId(item.getInsoleId());
                productItem1.setSizeId(item.getSizeId());
                productItem1.setSoleId(item.getSoleId());
                productItem1.setPrice(item.getPrice());
                ProductItem productItem2 = productItemRepository.save(productItem1);
                imageService.updateImage(item.getImageList(), productItem2.getId());
                productItemList.add(productItem2);
            }
            return productItemList;
        }

        throw new BadRequestException("Cập nhật sản phẩm thất bại");
    }

    public List<ProductItemDTO> findAllProductItemList() {
        List<ProductItemDTO> lst = MapperUtil.mapList(productItemRepository.findAllByStatus(true), ProductItemDTO.class);
        for (ProductItemDTO item : lst){
            item.getImageList().addAll(imageService.findAllByProductItemId(item.getId()));
        }
        return lst;
    }

    public Page<ProductItemDTO> findAllProductItemPage(Pageable pageable) {
        Page<ProductItem> page = productItemRepository.findAllByStatus(true, pageable);
        Page<ProductItemDTO> productItemDTO1S = page.map(new Function<ProductItem, ProductItemDTO>() {
            @Override
            public ProductItemDTO apply(ProductItem productItem) {
                ProductItemDTO dto = MapperUtil.map(productItem, ProductItemDTO.class);
                return dto;
            }
        });
        for(ProductItemDTO item : productItemDTO1S){
            item.getImageList().addAll(imageService.findAllByProductItemId(item.getProductId()));
        }
        return productItemDTO1S;
    }


    public Page<ProductItemDTO> findByNameContaining(String name, Pageable pageable) {

        if (!DataUtil.isNullObject(name)) {
            Page<ProductItem> productItems = this.productItemRepository.findByStatusAndNameContaining(true, name, pageable);
            Page<ProductItemDTO> productItemDTOS = productItems.map(new Function<ProductItem, ProductItemDTO>() {
                @Override
                public ProductItemDTO apply(ProductItem productItem) {
                    ProductItemDTO dto = MapperUtil.map(productItem, ProductItemDTO.class);
                    return dto;
                }
            });
            for(ProductItemDTO item : productItemDTOS){
                item.getImageList().addAll(imageService.findAllByProductItemId(item.getProductId()));
            }
            return productItemDTOS;
        }
        Page<ProductItem> productItem1s = this.productItemRepository.findAllByStatus(true, pageable);
        Page<ProductItemDTO> productItemDTO1S = productItem1s.map(new Function<ProductItem, ProductItemDTO>() {
            @Override
            public ProductItemDTO apply(ProductItem productItem) {
                ProductItemDTO dto = MapperUtil.map(productItem, ProductItemDTO.class);
                return dto;
            }
        });
        for(ProductItemDTO item : productItemDTO1S){
            item.getImageList().addAll(imageService.findAllByProductItemId(item.getProductId()));
        }
        return productItemDTO1S;
    }

    public ProductItemDTO findById(Long id) {
        Optional<ProductItem> productItem = this.productItemRepository.findByIdAndStatus(id, true);
        if (productItem.isPresent()) {
            ProductItemDTO dto = MapperUtil.map(productItem.get(), ProductItemDTO.class);
            dto.setImageList(imageService.findAllByProductItemId(id));
            return dto;
        }
        throw new BadRequestException("Không tìm thấy sản phẩm chi tiết này");
    }

    public void deleteProductItem(List<Long> ids) {
        if (ids.size() > 0) {
            for (Long id : ids) {
                Optional<ProductItem> productItem = productItemRepository.findById(id);
                productItem.get().setStatus(false);
                this.productItemRepository.save(productItem.get());
            }
        }
    }

    public List<ProductItemDTO> findProductItemByProjectId(Long projectId){
        List<ProductItemDTO> lst = MapperUtil.mapList(productItemRepository.findAllByProductIdAndStatus(projectId, true), ProductItemDTO.class);
        for (ProductItemDTO item : lst){
            List<ImageDTO> imageDTOList = imageService.findAllByProductItemId(item.getId());
            item.getImageList().addAll(imageDTOList);
        }
        return lst;
    }




}
