package com.datn.meou.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.datn.meou.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    private final ColorService colorService;

    private final SoleService soleService;

    private final InsoleSerivce insoleSerivce;

    private final SizeService sizeService;

    public Product saveProduct(ProductDTO dto) {
        if (DataUtil.isNullObject(dto.getName())) {
            throw new BadRequestException("Tên không được để trống");
        }
        if (DataUtil.isNullObject(dto.getImage())) {
            throw new BadRequestException("Ảnh không được để trống");
        }
        if (DataUtil.isNullObject(dto.getStatus())) {
            throw new BadRequestException("Chưa chọn trạng thái sản phẩm");
        }
        if(DataUtil.isNullObject(dto.getBrandId())){
            throw new BadRequestException("Thương hiệu không được để trống");
        }
        if(brandService.findById(dto.getBrandId()) == null){
            throw new BadRequestException("Thương hiệu không tồn tại");
        }
        Product product = Product
                .builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .image(dto.getImage())
                .status(dto.getStatus())
                .brandId(dto.getBrandId())
                .build();
        this.productRepository.save(product);

        return product;
    }

    public Product updateProduct(ProductDTO dto) {
        if (DataUtil.isNullObject(dto.getName())) {
            throw new BadRequestException("Tên không được để trống");
        }
        if (DataUtil.isNullObject(dto.getImage())) {
            throw new BadRequestException("Ảnh không được để trống");
        }
        if (DataUtil.isNullObject(dto.getStatus())) {
            throw new BadRequestException("Chưa chọn trạng thái sản phẩm");
        }
        if(DataUtil.isNullObject(dto.getBrandId())){
            throw new BadRequestException("Thương hiệu không được để trống");
        }
        if(brandService.findById(dto.getBrandId()) == null){
            throw new BadRequestException("Thương hiệu không tồn tại");
        }
        Optional<Product> productOptional = this.productRepository.findByIdAndStatusGreaterThan(dto.getId(), 0);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(dto.getName());
            product.setStatus(dto.getStatus());
            product.setImage(dto.getImage());
            product.setBrandId(dto.getBrandId());
            this.productRepository.save(product);

            return product;
        }
        throw new BadRequestException("Không có sản phẩm này");
    }

    public List<ProductDTO> findAllProductList() {
        ProductDTO dto = new ProductDTO();
        return productRepository.advancedSearch(dto);
    }

    public Page<ProductDTO> findAllProductPage(Pageable pageable) {
        ProductDTO dto = new ProductDTO();
        return this.productRepository.advancedSearchPage(dto, pageable);
    }

    public Page<ProductDTO> findByNameContaining(String name, Pageable pageable) {
        if (!DataUtil.isNullObject(name)) {
            ProductDTO dto = new ProductDTO();
            dto.setName(name);
            return this.productRepository.advancedSearchPage(dto, pageable);
        }
        return this.findAllProductPage(pageable);
    }

    public Product findById(Long id) {

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        throw new BadRequestException("Không tìm thấy sản phẩm");
    }

    public ProductDTO findProductById(Long id) {
        ProductDTO dto = new ProductDTO();
        dto.setId(id);
        List<ProductDTO> productDTOList = productRepository.advancedSearch(dto);
        if (!productDTOList.isEmpty()) {
            return productDTOList.get(0);
        }
        throw new BadRequestException("Không tìm thấy sản phẩm");
    }

    public void deleteProduct(List<Long> ids) {
        if (ids.size() > 0) {
            for (Long id : ids) {
                Product product = findById(id);
                List<ProductItem> productItems = productItemRepository.findAllByProductIdAndStatusGreaterThan(id, 0);
                if (!productItems.isEmpty()) {
                    throw new BadRequestException("Không thể xóa sản phẩm");
                }
                product.setStatus(0);
                this.productRepository.save(product);
            }
        }
    }

    public ResponseEntity<?> findByIdForOnline(Long id) {
        Map<String, Object> map = new HashMap<>();
        if (DataUtil.isNullObject(id)) {
            throw new BadRequestException("Phải truyền id của sản phẩm");
        }
        ProductDTO dto = this.productRepository.getByIdForOnline(id);
        if (dto != null) {
            map.put("product", dto);
        } else {
            throw new BadRequestException("Không tìm thấy sản phẩm này");
        }
        map.put("size", this.sizeService.getAllSizeByProductId(id));
        map.put("sole", this.soleService.getAllSoleByProductId(id));
        map.put("insole", this.insoleSerivce.getAllInsoleByProductId(id));
        map.put("color", this.colorService.getAllColorByProductId(id));
        return ResponseUtil.ok(map);
    }

}
