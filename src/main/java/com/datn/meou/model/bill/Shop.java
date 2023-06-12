package com.datn.meou.model.bill;

import com.datn.meou.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    private String code;
    private Double transportFee;
    private String paymentMethod;
    private Double price;
    private Double totalPrice;
    private String note;
    private String phone;
    private String email;
    private String fullname;
    private String addressTransfer;
    private List<Cart> cart;
    private List<Integer> voucherId;
}
