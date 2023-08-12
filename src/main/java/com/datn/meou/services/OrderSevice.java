package com.datn.meou.services;


import com.datn.meou.entity.Account;
import com.datn.meou.entity.Orders;
import com.datn.meou.entity.Transaction;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.OrderDTO;
import com.datn.meou.model.ProductItemDTO;
import com.datn.meou.repository.OrderStatusRepository;
import com.datn.meou.repository.OrdersRepository;
import com.datn.meou.repository.TransactionRepository;
import com.datn.meou.util.DataUtil;
import com.datn.meou.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderSevice {

    private final OrdersRepository ordersRepository;

    private final OrderStatusRepository orderStatusRepository;

    private final AccountService accountService;

    private final TransactionRepository transactionRepository;

    public Page<OrderDTO> findAll(OrderDTO dto, Pageable pageable) {
        Page<OrderDTO> pages = this.ordersRepository.findAll(dto, pageable);
        for (OrderDTO orderDTO : pages) {
            if (!DataUtil.isNullObject(orderDTO.getTypeOrder())) {
                if (orderDTO.getTypeOrder() == 1) {
                    orderDTO.setTypeOrders("Đặt tại Quầy");
                } else {
                    orderDTO.setTypeOrders("Đặt Online");
                }
            }
        }
        return pages;
    }

    public Transaction changeStatusByOrder(Long idOrder, Long idStatus, String note) {
        Account account = this.accountService.getCurrentUser();
        Optional<Orders> orders = this.ordersRepository.findById(idOrder);
        if (orders.isPresent()) {
            Orders orders1 = orders.get();
            orders1.setStatusId(idStatus);
            this.ordersRepository.save(orders1);
            Transaction transaction = Transaction
                    .builder()
                    .orderId(idOrder)
                    .accountId(account.getId())
                    .note(note)
                    .totalPrice(orders1.getTotalPrice())
                    .build();
            this.transactionRepository.save(transaction);
            return transaction;
        }
        throw new BadRequestException("Không tìm thấy id của đơn hàng này");
    }


    public ResponseEntity<?> createOrderByCounterSale(OrderDTO orderDTO) {
        Account account = this.accountService.getCurrentUser();
        String codeOrder = getCodeForOrder("MEOU1_");

        if (DataUtil.isNullObject(orderDTO.getPaymentMethod())) {
            throw new BadRequestException("Phải nhập phương thức thanh toán");
        }

        if (DataUtil.isNullObject(orderDTO.getPhoneCustomer())) {
            throw new BadRequestException("Phải nhập số điện thoại khách hàng");
        }
        if (DataUtil.isNullObject(orderDTO.getNameCustomer())) {
            throw new BadRequestException("Phải nhập tên khách hàng");
        }

        if (DataUtil.isNullObject(orderDTO.getAddressCustomer())) {
            throw new BadRequestException("Phải nhập địa chỉ khách hàng");
        }

        if (DataUtil.isNullObject(orderDTO.getEmailCustomer())) {
            throw new BadRequestException("Phải nhập email khách hàng");
        }

        Orders orders = Orders
                .builder()
                .accountId(account.getId())
                .note(orderDTO.getNote())
                .paymentMethod(orderDTO.getPaymentMethod())
                .typeOrder(1)
                .code(codeOrder)
                .statusId(1L)
                .voucherId(orderDTO.getVoucherId())
                .addressCustomer(orderDTO.getAddressCustomer())
                .emailCustomer(orderDTO.getEmailCustomer())
                .nameCustomer(orderDTO.getNameCustomer())
                .phoneCustomer(orderDTO.getPhoneCustomer())
                .build();
        this.ordersRepository.save(orders);


        return ResponseUtil.ok("Thêm mới thành công");
    }

    private String getCodeForOrder(String code) {
        List<Orders> ordersCheck = this.ordersRepository.findByCodeContainingOrderByIdDesc(code);
        String codeOld = ordersCheck.get(0).getCode();
        String code1 = codeOld.substring(0, 6);
        String code2 = codeOld.substring(6);
        Integer numberUp = Integer.parseInt(code2);
        numberUp = numberUp + 1;
        String numberNew = numberUp.toString();
        return code1 + numberNew;


    }


}
