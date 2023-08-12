package com.datn.meou.entity;


import com.datn.meou.model.OrderDTO;
import com.datn.meou.model.ProductItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@SqlResultSetMapping(
        name = "getAllOrder",
        classes = {
                @ConstructorResult(
                        targetClass = OrderDTO.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "code", type = String.class),
                                @ColumnResult(name = "createdDate", type = Date.class),
                                @ColumnResult(name = "addressCustomer", type = String.class),
                                @ColumnResult(name = "emailCustomer", type = String.class),
                                @ColumnResult(name = "nameCustomer", type = String.class),
                                @ColumnResult(name = "note", type = String.class),
                                @ColumnResult(name = "totalPrice", type = Double.class),
                                @ColumnResult(name = "typeOrder", type = Integer.class),
                                @ColumnResult(name = "phoneCustomer", type = String.class),
                                @ColumnResult(name = "paymentMethod", type = String.class),
                                @ColumnResult(name = "valueStatus", type = String.class),
                                @ColumnResult(name = "username", type = String.class)
                        }
                )
        }
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "dotn_order")
public class Orders extends BaseEntity {
    private Long accountId;
    private Long voucherId;
    private String code;
    private Double totalPrice;
    private String paymentMethod;
    private Integer typeOrder;
    private String note;
    private Long statusId;
    private String nameCustomer;
    private String phoneCustomer;
    private String addressCustomer;
    private String emailCustomer;
    private Long customerId;


}
