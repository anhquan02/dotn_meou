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
        if(DataUtil.isNullObject(dto.getName())){
            throw new BadRequestException("Tên không được để trống");
        }
        if(DataUtil.isNullObject(dto.getImage())){
            throw new BadRequestException("Ảnh không được để trống");
        }
        if(DataUtil.isNullObject(dto.getStatus())){
            throw new BadRequestException("Chưa chọn trạng thái sản phẩm");
        }
        Optional<Product> productOptional = this.productRepository.findByIdAndStatusGreaterThan(dto.getId(), 0);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(dto.getName());
            product.setStatus(dto.getStatus());
            product.setImage(dto.getImage());
            this.productRepository.save(product);

            return product;
        }
        throw new BadRequestException("Không có sản phẩm này");
    }
    public List<Product> findAllProductList() {
        return productRepository.findAllByStatusGreaterThan(0);
    }

    public Page<Product> findAllProductPage(Pageable pageable) {
        return productRepository.findAllByStatusGreaterThan(0, pageable);
    }

    public Page<Product> findByNameContaining(String name, Pageable pageable) {
        if (!DataUtil.isNullObject(name)) {
            return this.productRepository.findByStatusGreaterThanAndNameContaining(0, name, pageable);
        }
        return this.productRepository.findAllByStatusGreaterThan(0, pageable);
    }

    public Product findById(Long id) {
        Optional<Product> product = this.productRepository.findByIdAndStatusGreaterThan(id, 0);
        if (product.isPresent()) {
            return product.get();
        }
        throw new BadRequestException("Không tìm thấy size này");
    }
    public void deleteProduct(List<Long> ids) {
        if (ids.size() > 0) {
            for (Long id : ids) {
                Product product = findById(id);
                List<ProductItem> productItems = productItemRepository.findAllByProductIdAndStatusGreaterThan(id, 0);
                if(!productItems.isEmpty()){
                    throw new BadRequestException("Không thể xóa sản phẩm");
                }
                product.setStatus(0);
                this.productRepository.save(product);
            }
        }
    }

}
