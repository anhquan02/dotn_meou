package com.datn.meou.repository;

import com.datn.meou.model.ProductItemDTO;

import java.util.List;

public interface ProductItemRepositoryCustom {

    List<ProductItemDTO> searchProductForCounterSale(ProductItemDTO dto);
}
