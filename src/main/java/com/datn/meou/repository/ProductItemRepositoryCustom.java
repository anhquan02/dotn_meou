package com.datn.meou.repository;

import com.datn.meou.model.ProductItemDTO;
import com.datn.meou.model.StatisticalDTO;
import com.datn.meou.model.StatisticalDTOS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ProductItemRepositoryCustom {

    List<ProductItemDTO> searchProductForCounterSale(ProductItemDTO dto);

    List<StatisticalDTOS> topSales();

    List<StatisticalDTOS> getStatisticalByDate(Date fromDate, Date toDate);

}
