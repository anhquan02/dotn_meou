package com.datn.meou.repository;

import com.datn.meou.entity.Product;
import com.datn.meou.entity.ProductItem;
import com.datn.meou.model.ProductDTO;
import com.datn.meou.model.ProductItemDTO;
import com.datn.meou.model.StatisticalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductDTO> advancedSearch(ProductDTO dto);

    Page<ProductDTO> advancedSearchPage(ProductDTO dto, Pageable pageable);

    ProductDTO getByIdForOnline(Long id);

    StatisticalDTO getForMonthly();

    StatisticalDTO getForToday();
}
