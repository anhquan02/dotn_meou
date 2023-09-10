package com.datn.meou.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoucherDTO extends BaseModel {

    @NotEmpty(message = "Tên không được rỗng")
    @Size(min = 2, max = 500, message = "Tên phải từ 2 đến 500 ký tự")
    private String name;

    @NotNull(message = "Ngày bắt đầu không được rỗng")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dayStart;

    @NotNull(message = "Ngày kết thúc không được rỗng")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dayEnd;

    @NotNull(message = "Gía trị voucher không được rỗng")
    private BigDecimal value;

    private Boolean status;
}
