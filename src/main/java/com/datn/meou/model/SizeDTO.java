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
public class SizeDTO extends BaseModel {
    @NotEmpty(message = "Tên không được rỗng")
    @Size(min = 1, max = 2, message = "Size không qua 2 ký tự")
    private String name;
}
