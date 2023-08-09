package com.datn.meou.services;


import com.datn.meou.entity.Orders;
import com.datn.meou.entity.Transaction;
import com.datn.meou.model.TransactionDTO;
import com.datn.meou.repository.OrdersRepository;
import com.datn.meou.repository.TransactionRepository;
import com.datn.meou.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final OrdersRepository ordersRepository;

//    public Page<TransactionDTO> findAll(Integer currentPage, Integer size) {
//        Pageable pageable = PageRequest.of(currentPage, size);
//        Page<Transaction> transactionPage = this.transactionRepository.findAllByDeleted(true, pageable);
//        Page<TransactionDTO> dtos = MapperUtil.mapEntityPageIntoDtoPage(transactionPage, TransactionDTO.class);
//        for (TransactionDTO dto : dtos) {
//            Optional<Orders> orders = this.ordersRepository.findByIdAndDeleted(dto.getOrderId(), true);
//            if (orders.isPresent()) {
//                Orders orders1 = orders.get();
//                if (orders1.getTypeOrder() == 1) {
//                    dto.setTypeOrders("Đặt Online");
//                } else if (orders1.getTypeOrder() == 2) {
//                    dto.setTypeOrders("Đặt tại quầy");
//                }
//                dto.setCodeOrder(orders1.getCode());
//                dto.setNameCustomer(orders1.getNameCustomer());
//                dto.setPhoneCustomer(orders1.getPhoneCustomer());
//            }
//        }
//        return dtos;
//    }
//
//    public Page<TransactionDTO> findAllByOrderName(String name, Integer currentPage, Integer size) {
//        Pageable pageable = PageRequest.of(currentPage, size);
//        Page<TransactionDTO> dtos = this.transactionRepository.findByNameOrders(name, pageable);
//        return dtos;
//    }

}
