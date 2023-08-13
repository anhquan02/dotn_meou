package com.datn.meou.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeStatus extends BaseModel {
    private Long idOrder;
    private Long idStatus;
    private String note;
}
