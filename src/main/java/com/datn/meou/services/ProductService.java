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

    public Product saveProduct(ProductDTO dto) {

        Product product = Product
                .builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .brandId(dto.getBrandId())
                .image(dto.getNameImage())
                .build();
        product.setStatus(true);
        this.productRepository.save(product);

        return product;
    }
    public Product updateProduct(ProductDTO dto) {
        Optional<Product> productOptional = this.productRepository.findByIdAndStatus(dto.getId(), true);
        if (productOptional.isPresent()) {
            Product product = MapperUtil.map(dto, Product.class);
            product.setName(dto.getName());
            product.setPrice(dto.getPrice());
            product.setStatus(dto.getStatus());
            product.setBrandId(dto.getBrandId());
            this.productRepository.save(product);

            return product;
        }
        throw new BadRequestException("Không có sản phẩm này");
    }
    public List<Product> findAllProductList() {
        return productRepository.findAllByStatus(true);
    }

    public Page<Product> findAllProductPage(Pageable pageable) {
        return productRepository.findAllByStatus(true, pageable);
    }

    public Page<Product> findByNameContaining(String name, Pageable pageable) {
        if (!DataUtil.isNullObject(name)) {
            return this.productRepository.findByStatusAndNameContaining(true, name, pageable);
        }
        return this.productRepository.findAllByStatus(true, pageable);
    }

    public Product findById(Long id) {
        Optional<Product> product = this.productRepository.findByIdAndStatus(id, true);
        if (product.isPresent()) {
            return product.get();
        }
        throw new BadRequestException("Không tìm thấy size này");
    }
    public void deleteProduct(List<Long> ids) {
        if (ids.size() > 0) {
            for (Long id : ids) {
                Product product = findById(id);
                List<ProductItem> productItems = productItemRepository.findAllByProductIdAndStatus(id, true);
                if(!productItems.isEmpty()){
                    throw new BadRequestException("Không thể xóa sản phẩm");
                }
                product.setStatus(false);
                this.productRepository.save(product);
            }
        }
    }

}
