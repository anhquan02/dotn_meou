package com.datn.meou.model.bill;

import com.datn.meou.entity.Voucher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse {
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
    private Integer status;
    private List<ProductBill> product;
    private List<Voucher> voucher;

    public BillResponse(String code, Double transportFee, String paymentMethod, Double price, Double totalPrice, String note, String phone, String email, String fullname, String addressTransfer, Integer status) {
        this.code = code;
        this.transportFee = transportFee;
        this.paymentMethod = paymentMethod;
        this.price = price;
        this.totalPrice = totalPrice;
        this.note = note;
        this.phone = phone;
        this.email = email;
        this.fullname = fullname;
        this.addressTransfer = addressTransfer;
        this.status = status;
    }
}
