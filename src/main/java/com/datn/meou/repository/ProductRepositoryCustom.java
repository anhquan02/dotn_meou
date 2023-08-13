package com.datn.meou.repository;

import com.datn.meou.entity.Product;
import com.datn.meou.entity.ProductItem;
import com.datn.meou.model.ProductDTO;
import com.datn.meou.model.ProductItemDTO;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductDTO> advancedSearch(ProductDTO dto);
}
