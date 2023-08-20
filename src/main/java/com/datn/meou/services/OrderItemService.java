package com.datn.meou.services;

import com.datn.meou.entity.OrderItem;
import com.datn.meou.entity.ProductItem;
import com.datn.meou.entity.Transaction;
import com.datn.meou.entity.TransactionStatus;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.OrderDTO;
import com.datn.meou.model.OrderItemDTO;
import com.datn.meou.repository.*;
import com.datn.meou.util.DataUtil;
import com.datn.meou.util.MapperUtil;
import com.datn.meou.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemService {
    private final OrdersRepository ordersRepository;

    private final OrderItemRepository orderItemRepository;

    private final TransactionStatusRepository transactionStatusRepository;

    private final ProductItemRepository productItemRepository;

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
            List<OrderItemDTO> orderItemDTOS = MapperUtil.mapList(orderItems, OrderItemDTO.class);
            if (!DataUtil.isNullObject(orderItemDTOS)) {
                for (OrderItemDTO orderItemDTO : orderItemDTOS) {
                    Optional<ProductItem> productItem = this.productItemRepository.findByIdAndStatusGreaterThan(orderItemDTO.getId(), 0);
                    if (productItem.isPresent()) {

                    }
                }
            }
            List<TransactionStatus> transactions = this.transactionStatusRepository.findByOrderId(idOrder);
            Map<String, Object> map = new HashMap<>();
            map.put("order", orderDTO);
            map.put("orders", orderItems);
            map.put("transactions", transactions);
            return ResponseUtil.ok(map);
        }
        throw new BadRequestException("Không tìm thấy id của đơn hàng này");

    }
}
