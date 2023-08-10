package com.datn.meou.services;


import com.datn.meou.entity.Account;
import com.datn.meou.entity.Orders;
import com.datn.meou.entity.Transaction;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.OrderDTO;
import com.datn.meou.repository.OrderStatusRepository;
import com.datn.meou.repository.OrdersRepository;
import com.datn.meou.repository.TransactionRepository;
import com.datn.meou.util.DataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
