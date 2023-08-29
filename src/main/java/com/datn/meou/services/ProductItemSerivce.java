package com.datn.meou.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.datn.meou.entity.*;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.*;
import com.datn.meou.repository.ImageRepository;
import com.datn.meou.repository.OrderItemRepository;
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
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final SoleService soleService;
    private final BrandService brandService;
    private final ImageService imageService;
    private final InsoleSerivce insoleSerivce;
    private final ColorService colorService;
    private final SizeService sizeService;

    private final ImageRepository imageRepository;

    private final ProductRepository productRepository;

    public List<ProductItemDTO> saveProductItem(ProductItemDTOS dtos) {
        if (dtos.getDto() != null) {
            List<ProductItemDTO> productItemList = new ArrayList<>();
            for (ProductItemDTO item : dtos.getDto()) {
                ProductItem productItemCheck =
                        productItemRepository.findByProductIdAndColorIdAndInsoleIdAndSizeIdAndSoleId(item.getProductId(), item.getColorId(), item.getInsoleId(), item.getSizeId(), item.getSoleId());
                if (DataUtil.isNullObject(item.getProductId())) {
                    throw new BadRequestException("id sản phẩm không được để trống");
                }

                if (DataUtil.isNullObject(item.getSizeId())) {
                    throw new BadRequestException("Size không để trống");
                }

                if (DataUtil.isNullObject(item.getInsoleId())) {
                    throw new BadRequestException("Lót giày không để trống");
                }

                if (DataUtil.isNullObject(item.getSoleId())) {
                    throw new BadRequestException("Đế giày không để trống");
                }

                if (DataUtil.isNullObject(item.getColorId())) {
                    throw new BadRequestException("Màu sắc không để trống");
                }

                if (DataUtil.isNullObject(item.getQuantity())) {
                    throw new BadRequestException("Số lượng không để trống");
                }
                if (item.getQuantity() < 0) {
                    throw new BadRequestException("Số lượng không được nhỏ hơn 0");
                }
                if (DataUtil.isNullOrEmpty(item.getImageList())) {
                    throw new BadRequestException("Ảnh không được để trống");
                }
                if (DataUtil.isNullObject(item.getPrice())) {
                    throw new BadRequestException("Giá không được để trống");
                }
                if (DataUtil.isNullObject(item.getStatus())) {
                    throw new BadRequestException("Trạng thái không được để trống");
                }
                Product product = productService.findById(item.getProductId());
                if (product == null) {
                    throw new BadRequestException("Sản phẩm không tồn tại");
                }
                if (colorService.findById(item.getColorId()) == null) {
                    throw new BadRequestException("Màu sắc không tồn tại");
                }

                if (soleService.findById(item.getSoleId()) == null) {
                    throw new BadRequestException("Đế giày không tồn tại");
                }

                if (insoleSerivce.findById(item.getInsoleId()) == null) {
                    throw new BadRequestException("Lót giày không tồn tại");
                }

                if (sizeService.findById(item.getSizeId()) == null) {
                    throw new BadRequestException("Size không tồn tại");
                }
                if (item.getImageList().stream().count() > 6) {
                    throw new BadRequestException("Một sản phẩm chỉ được tối đa 6 ảnh");
                }
                if (productItemCheck == null) {
                    ProductItem productItem = ProductItem
                            .builder()
                            .productId(item.getProductId())
                            .name(item.getName())
                            .colorId(item.getColorId())
                            .sizeId(item.getSizeId())
                            .soleId(item.getSoleId())
                            .insoleId(item.getInsoleId())
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .status(item.getStatus())
                            .build();
                    ProductItemDTO productItemDTO = MapperUtil.map(productItemRepository.save(productItem), ProductItemDTO.class);
                    productItemDTO.getImageList().addAll(MapperUtil.mapList(imageService.saveListImage(item.getImageList(), productItemDTO.getId()), ImageDTO.class));
                    productItemList.add(productItemDTO);

                } else {
                    productItemCheck.setQuantity(item.getQuantity() + productItemCheck.getQuantity());
                    productItemRepository.save(productItemCheck);
                    productItemList.add(MapperUtil.map(productItemCheck, ProductItemDTO.class));
                }

            }
            return productItemList;
        }
        throw new BadRequestException("Lưu sản phẩm thất bại");
    }

    public List<ProductItemDTO> updateProductItem(ProductItemDTOS dtos) {
        if (dtos.getDto() != null) {
            List<ProductItemDTO> productItemList = new ArrayList<>();
            for (ProductItemDTO item : dtos.getDto()) {
                if (DataUtil.isNullObject(item.getSizeId())) {
                    throw new BadRequestException("Size không để trống");
                }

                if (DataUtil.isNullObject(item.getInsoleId())) {
                    throw new BadRequestException("Lót giày không để trống");
                }

                if (DataUtil.isNullObject(item.getSoleId())) {
                    throw new BadRequestException("Đế giày không để trống");
                }

                if (DataUtil.isNullObject(item.getColorId())) {
                    throw new BadRequestException("Màu sắc không để trống");
                }

                if (DataUtil.isNullObject(item.getQuantity())) {
                    throw new BadRequestException("Số lượng không để trống");
                }
                if (item.getQuantity() < 0) {
                    throw new BadRequestException("Số lượng không được nhỏ hơn 0");
                }
                if (DataUtil.isNullObject(item.getPrice())) {
                    throw new BadRequestException("Giá không được để trống");
                }
                if (DataUtil.isNullObject(item.getStatus())) {
                    throw new BadRequestException("Trạng thái không được để trống");
                }
                Optional<ProductItem> productItem = productItemRepository.findById(item.getId());
                ProductItem productItem1 = productItem.get();
                if (productItem1 == null) {
                    throw new BadRequestException("sản phẩm không tồn tại");
                }

                if (!DataUtil.isNullObject(item.getSoleId())) {
                    if (soleService.findById(item.getSoleId()) == null) {
                        throw new BadRequestException("Đế giày không tồn tại");
                    }
                }
                if (!DataUtil.isNullObject(item.getInsoleId())) {
                    if (insoleSerivce.findById(item.getInsoleId()) == null) {
                        throw new BadRequestException("Lót giày không tồn tại");
                    }
                }
                if (!DataUtil.isNullObject(item.getSizeId())) {
                    if (sizeService.findById(item.getSizeId()) == null) {
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
                productItem1.setStatus(item.getStatus());
                ProductItemDTO productItemDTO = MapperUtil.map(productItemRepository.save(productItem1), ProductItemDTO.class);
                productItemList.add(productItemDTO);
            }
            return productItemList;
        }

        throw new BadRequestException("Cập nhật sản phẩm thất bại");
    }

    public List<Image> updateImage(ImageDTOS imageDTOList){
        Long lengthImage = imageService.findAllByProductItemId(imageDTOList.getProductItemID()).stream().count();
        for (ImageDTO imageDTO : imageDTOList.getImageDTOList()) {
            if(imageService.findById(imageDTO.getId()) == null && imageDTO.getId() > 0){
                throw new BadRequestException("Ảnh không tồn tại để xóa");
            }
            if (DataUtil.isNullObject(imageDTO.getName()) && imageDTO.getId() > 0) {
                lengthImage--;
            }
            if(!DataUtil.isNullObject(imageDTO.getName()) && imageDTO.getId() == 0){
                lengthImage++;
            }
        }
        if (lengthImage > 6) {
            throw new BadRequestException("Một sản phẩm  chỉ được tối đa 6 ảnh");
        }
        return imageService.updateImage(imageDTOList.getImageDTOList(), imageDTOList.getProductItemID());
    };

    public List<ProductItemDTO> findAllProductItemList() {
        List<ProductItemDTO> lst = MapperUtil.mapList(productItemRepository.findAllByStatusGreaterThan(0), ProductItemDTO.class);
        for (ProductItemDTO item : lst) {
            item.getImageList().addAll(imageService.findAllByProductItemId(item.getId()));
        }
        return lst;
    }

    public Page<ProductItemDTO> findAllProductItemPage(Pageable pageable) {
        Page<ProductItem> page = productItemRepository.findAllByStatusGreaterThan(0, pageable);
        Page<ProductItemDTO> productItemDTO1S = page.map(new Function<ProductItem, ProductItemDTO>() {
            @Override
            public ProductItemDTO apply(ProductItem productItem) {
                ProductItemDTO dto = MapperUtil.map(productItem, ProductItemDTO.class);
                return dto;
            }
        });
        for (ProductItemDTO item : productItemDTO1S) {
            item.getImageList().addAll(imageService.findAllByProductItemId(item.getProductId()));
        }
        return productItemDTO1S;
    }


    public Page<ProductItemDTO> findByNameContaining(String name, Pageable pageable) {

        if (!DataUtil.isNullObject(name)) {
            Page<ProductItem> productItems = this.productItemRepository.findByStatusGreaterThanAndNameContaining(0, name, pageable);
            Page<ProductItemDTO> productItemDTOS = productItems.map(new Function<ProductItem, ProductItemDTO>() {
                @Override
                public ProductItemDTO apply(ProductItem productItem) {
                    ProductItemDTO dto = MapperUtil.map(productItem, ProductItemDTO.class);
                    return dto;
                }
            });
            for (ProductItemDTO item : productItemDTOS) {
                item.getImageList().addAll(imageService.findAllByProductItemId(item.getProductId()));
            }
            return productItemDTOS;
        }
        Page<ProductItem> productItem1s = this.productItemRepository.findAllByStatusGreaterThan(0, pageable);
        Page<ProductItemDTO> productItemDTO1S = productItem1s.map(new Function<ProductItem, ProductItemDTO>() {
            @Override
            public ProductItemDTO apply(ProductItem productItem) {
                ProductItemDTO dto = MapperUtil.map(productItem, ProductItemDTO.class);
                return dto;
            }
        });
        for (ProductItemDTO item : productItemDTO1S) {
            item.getImageList().addAll(imageService.findAllByProductItemId(item.getProductId()));
        }
        return productItemDTO1S;
    }

    public ProductItemDTO findById(Long id) {
        Optional<ProductItem> productItem = this.productItemRepository.findByIdAndStatusGreaterThan(id, 0);
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
                if (productItem.isEmpty()) {
                    throw new BadRequestException("Sản phẩm chi tiết này không tồn tại");
                }
                List<OrderItem> lstOrderItem = orderItemRepository.findAllByProductItemId(productItem.get().getId());
                if (lstOrderItem.isEmpty()) {
                    throw new BadRequestException("không thể xóa sản phẩm này");
                }
                productItem.get().setStatus(0);
                this.productItemRepository.save(productItem.get());
            }
        }
    }

    public List<ProductItemDTO> findProductItemByProjectId(Long projectId) {
        List<ProductItemDTO> lst = MapperUtil.mapList(productItemRepository.findAllByProductIdAndStatusGreaterThan(projectId, 0), ProductItemDTO.class);
        for (ProductItemDTO item : lst) {
            List<ImageDTO> imageDTOList = imageService.findAllByProductItemId(item.getId());
            item.getImageList().addAll(imageDTOList);
        }
        return lst;
    }

    public List<ProductItemDTO> searchProductForCounterSale(ProductItemDTO dto) {
        List<ProductItemDTO> productItemDTOS = this.productItemRepository.searchProductForCounterSale(dto);
        for (ProductItemDTO productItemDTO : productItemDTOS) {
            List<ImageDTO> images = this.imageService.findAllByProductItemId(productItemDTO.getId());
            productItemDTO.setImageList(images);
        }

        return productItemDTOS;

    }

    public Object chooseForOnline(Long soleId, Long
            sizeId, Long productId, Long insoleId, Long colorId) {
        if (DataUtil.isNullObject(sizeId)) {
            throw new BadRequestException("Phải chọn size của sản phẩm");
        }
        if (DataUtil.isNullObject(productId)) {
            throw new BadRequestException("Chưa có sản phẩm");
        }
        if (DataUtil.isNullObject(insoleId)) {
            throw new BadRequestException("Phải chọn dây giày của sản phẩm");
        }
        if (DataUtil.isNullObject(colorId)) {
            throw new BadRequestException("Phải chọn màu của sản phẩm");
        }
        if (DataUtil.isNullObject(soleId)) {
            throw new BadRequestException("Phải chọn đế giày của sản phẩm");
        }
        Optional<ProductItem> item = this.productItemRepository.chooseForOnline(soleId, sizeId, productId, insoleId, colorId);
        if (item.isPresent()) {
            ProductItemDTO productItemDTO = MapperUtil.map(item.get(), ProductItemDTO.class);
            List<Image> images = this.imageRepository.findAllByProductItemId(productItemDTO.getId());
            productItemDTO.setImages(images);
            return productItemDTO;
        } else {
            ProductDTO dto = this.productRepository.getByIdForOnline(productId);
            dto.setQuantity(BigDecimal.valueOf(0));
            return dto;
        }

    }

}
