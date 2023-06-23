package com.datn.meou.services;

import com.datn.meou.entity.OrderItem;
import com.datn.meou.entity.Orders;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.OrderItemDTO;
import com.datn.meou.repository.OrderItemRepository;
import com.datn.meou.repository.OrdersRepository;
import com.datn.meou.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemService {
    private final OrdersRepository ordersRepository;

    private final OrderItemRepository orderItemRepository;

    public List<OrderItemDTO> getByIdOrder(Long idOrder) {
        Optional<Orders> orders = this.ordersRepository.findByIdAndDeleted(idOrder, true);
        if (orders.isEmpty()) {
            throw new BadRequestException("Không tìm thấy đơn hàng này");
        }
        List<OrderItem> orderItems = this.orderItemRepository.findByOrderId(idOrder);
        List<OrderItemDTO> orderItemDTOS = MapperUtil.mapList(orderItems, OrderItemDTO.class);
        return orderItemDTOS;
    }
}
