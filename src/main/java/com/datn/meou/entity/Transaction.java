package com.datn.meou.entity;


import com.datn.meou.model.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@SqlResultSetMapping(
        name = "getTransactionByCode",
        classes = {
                @ConstructorResult(
                        targetClass = TransactionDTO.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "codeOrder", type = String.class),
                                @ColumnResult(name = "creatDate", type = Date.class),
                                @ColumnResult(name = "nameCustomer", type = String.class),
                                @ColumnResult(name = "phoneCustomer", type = String.class),
                                @ColumnResult(name = "type", type = Integer.class),
                                @ColumnResult(name = "totalPrice", type = BigDecimal.class),
                        }
                )
        }
)


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "dotn_transaction")
public class Transaction extends BaseEntity {
    private Long orderId;
    private BigDecimal totalPrice;
    private Long accountId;

}
