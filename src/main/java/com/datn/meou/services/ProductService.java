package com.datn.meou.services;

import java.util.List;
import java.util.Optional;

import com.datn.meou.entity.Insole;
import com.datn.meou.entity.ProductItem;
import com.datn.meou.entity.Sole;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.ProductDTO;
import com.datn.meou.model.SoleDTO;
import com.datn.meou.repository.ProductItemRepository;
import com.datn.meou.util.DataUtil;
import com.datn.meou.util.MapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.datn.meou.entity.Product;
import com.datn.meou.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductItemRepository productItemRepository;

    private final BrandService brandService;

    public Product saveProduct(ProductDTO dto) {
        if(DataUtil.isNullObject(dto.getName())){
            throw new BadRequestException("Tên không được để trống");
        }
        if(DataUtil.isNullObject(dto.getImage())){
            throw new BadRequestException("Ảnh không được để trống");
        }
        if(DataUtil.isNullObject(dto.getStatus())){
            throw new BadRequestException("Chưa chọn trạng thái sản phẩm");
        }
        Product product = Product
                .builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .image(dto.getImage())
                .status(dto.getStatus())
                .build();
        this.productRepository.save(product);

        return product;
    }
    public Product updateProduct(ProductDTO dto) {
        Optional<Product> productOptional = this.productRepository.findByIdAndDeleted(dto.getId(), true);
        if(!DataUtil.isNullObject(dto.getBrandId())){
            if(brandService.findById(dto.getBrandId()) == null){
                throw new BadRequestException("Không tìm thấy thương hiệu này");
            }
        }
        if (productOptional.isPresent()) {
            Product product = MapperUtil.map(dto, Product.class);
            product.setName(dto.getName());
            product.setStatus(dto.getStatus());
            this.productRepository.save(product);

            return product;
        }
        throw new BadRequestException("Không có sản phẩm này");
    }
    public List<Product> findAllProductList() {
        return productRepository.findAllByDeleted(true);
    }

    public Page<Product> findAllProductPage(Pageable pageable) {
        return productRepository.findAllByDeleted(true, pageable);
    }

    public Page<Product> findByNameContaining(String name, Pageable pageable) {
        if (!DataUtil.isNullObject(name)) {
            return this.productRepository.findByDeletedAndNameContaining(true, name, pageable);
        }
        return this.productRepository.findAllByDeleted(true, pageable);
    }

    public Product findById(Long id) {
        Optional<Product> product = this.productRepository.findByIdAndDeleted(id, true);
        if (product.isPresent()) {
            return product.get();
        }
        throw new BadRequestException("Không tìm thấy size này");
    }
    public void deleteProduct(List<Long> ids) {
        if (ids.size() > 0) {
            for (Long id : ids) {
                Product product = findById(id);
                List<ProductItem> productItems = productItemRepository.findAllByProductIdAndDeleted(id, true);
                if(!productItems.isEmpty()){
                    throw new BadRequestException("Không thể xóa sản phẩm");
                }
                product.setDeleted(false);
                this.productRepository.save(product);
            }
        }
    }

}
