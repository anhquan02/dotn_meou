package com.datn.meou.repository;

import com.datn.meou.model.ProductDTO;

import java.util.List;

public interface ProductRepositoryCustom {

    List<ProductDTO> searchProductForCounterSale(String nameProuduct);
}
