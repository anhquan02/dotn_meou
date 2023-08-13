package com.datn.meou.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CounterSaleDTO {
    @Valid
    OrderDTO orderDTO;

    List<ProductItemDTO> productItemDTOS;
}
