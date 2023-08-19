package com.datn.meou.repository;

import com.datn.meou.model.ProductItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductItemRepositoryCustom {

    List<ProductItemDTO> searchProductForCounterSale(ProductItemDTO dto);

//    Page<ProductItemDTO> getAllProductItemByPage(Pageable pageable);
//
//    Page<ProductItemDTO> getAllProductItemByNameContaining(String name, Pageable pageable);
//

}
