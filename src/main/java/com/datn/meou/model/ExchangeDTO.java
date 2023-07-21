package com.datn.meou.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ExchangeDTO {


    private Long id;

    private Date createdDate;

    private Date updatedDate;

    private Boolean deleted;

    private Integer orderId;
    private Double totalPrice;
    private Integer accountId;
    private String nameCustomer;
    private String phoneCustomer;
    private String addressCustomer;
    private String emailCustomer;
}
