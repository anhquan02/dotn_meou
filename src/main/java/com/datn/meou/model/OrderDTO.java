package com.datn.meou.model;

import com.datn.meou.exception.CheckEmail;
import com.datn.meou.exception.CheckPhone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderDTO extends BaseModel {

    private Long accountId;
    private Long voucherId;
    private String code;
    private BigDecimal totalPrice;
    private String paymentMethod;
    private Integer typeOrder;
    private String note;
    private Long statusId;
    @NotEmpty(message = "Tên khách hàng không được rỗng")
    private String nameCustomer;
    @CheckPhone(message = "Số điện thoại không hợp lệ")
    @NotEmpty(message = "Số điện thoại không được rỗng")
    private String phoneCustomer;
    @NotEmpty(message = "Địa chỉ không được rỗng")
    private String addressCustomer;
    @CheckEmail(message = "Email không hợp lệ")
    @NotEmpty(message = "Email không được rỗng")
    private String emailCustomer;
    private Double voucher;
    private String status;
    private String typeOrders;
    private String nameStatus;
    private String valueStatus;
    private String username;
    private Long customerId;

    public OrderDTO(Long id, String code, Date createdDate, String addressCustomer,
                    String emailCustomer, String nameCustomer, String note, BigDecimal totalPrice,
                    Integer typeOrder, String phoneCustomer, String paymentMethod, String valueStatus,
                    String username, Long statusId) {
        this.id = id;
        this.code = code;
        this.createdDate = createdDate;
        this.addressCustomer = addressCustomer;
        this.emailCustomer = emailCustomer;
        this.nameCustomer = nameCustomer;
        this.note = note;
        this.totalPrice = totalPrice;
        this.typeOrder = typeOrder;
        this.phoneCustomer = phoneCustomer;
        this.paymentMethod = paymentMethod;
        this.valueStatus = valueStatus;
        this.username = username;
        this.statusId = statusId;
    }


}
