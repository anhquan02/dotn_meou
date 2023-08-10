package com.datn.meou.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsoleDTO extends BaseModel {
    @NotEmpty(message = "Tên không được rỗng")
    @Size(min = 2, max = 500, message = "Tên phải từ 2 đến 500 ký tự")
    private String name;
}
