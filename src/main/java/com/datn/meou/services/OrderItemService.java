package com.datn.meou.services;

import com.datn.meou.entity.OrderItem;
import com.datn.meou.entity.Transaction;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.OrderDTO;
import com.datn.meou.repository.OrderItemRepository;
import com.datn.meou.repository.OrdersRepository;
import com.datn.meou.repository.TransactionRepository;
import com.datn.meou.util.DataUtil;
import com.datn.meou.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemService {
    private final OrdersRepository ordersRepository;

    private final OrderItemRepository orderItemRepository;

    private final TransactionRepository transactionRepository;

    public ResponseEntity<?> getByIdOrder(Long idOrder) {
        if (!DataUtil.isNullObject(idOrder)) {
            OrderDTO orderDTO = this.ordersRepository.findByOrderId(idOrder);
            if (!DataUtil.isNullObject(orderDTO.getTypeOrder())) {
                if (orderDTO.getTypeOrder() == 1) {
                    orderDTO.setTypeOrders("Đặt tại Quầy");
                } else {
                    orderDTO.setTypeOrders("Đặt Online");
                }
            }
            List<OrderItem> orderItems = this.orderItemRepository.findAllByOrderId(idOrder);
            List<Transaction> transactions = this.transactionRepository.findByOrderId(idOrder);
            Map<String, Object> map = new HashMap<>();
            map.put("order", orderDTO);
            map.put("orders", orderItems);
            map.put("transactions", transactions);
            return ResponseUtil.ok(map);
        }
        throw new BadRequestException("Không tìm thấy id của đơn hàng này");

    }
}
