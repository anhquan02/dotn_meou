package com.datn.meou.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderStatusDTO extends BaseModel {

    @NotEmpty(message = "Value  cannot be empty.")
    private String valueStatus;
}
