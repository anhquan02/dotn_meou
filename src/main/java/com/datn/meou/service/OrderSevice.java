package com.datn.meou.service;


import com.datn.meou.entity.OrderStatus;
import com.datn.meou.entity.Orders;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.OrderDTO;
import com.datn.meou.repository.OrderStatusRepository;
import com.datn.meou.repository.OrdersRepository;
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
public class OrderSevice {

    private final OrdersRepository ordersRepository;

    private final OrderStatusRepository orderStatusRepository;

    public Page<Orders> findAll(Integer currentPage, Integer size) {
        Pageable pageable = PageRequest.of(currentPage, size);
        return this.ordersRepository.findAllByDeletedAndStatusIdOrderByCreatedDateDesc(true, 1L, pageable);
    }

    public Page<Orders> getStatus2(Integer currentPage, Integer size) {
        Pageable pageable = PageRequest.of(currentPage, size);
        return this.ordersRepository.findAllByDeletedAndStatusIdOrderByUpdatedDateDesc(true, 2L, pageable);
    }

    public Page<Orders> getStatus3(Integer currentPage, Integer size) {
        Pageable pageable = PageRequest.of(currentPage, size);
        return this.ordersRepository.findAllByDeletedAndStatusIdOrderByUpdatedDateDesc(true, 3L, pageable);
    }

    public Page<Orders> getStatus4(Integer currentPage, Integer size) {
        Pageable pageable = PageRequest.of(currentPage, size);
        return this.ordersRepository.findAllByDeletedAndStatusIdOrderByUpdatedDateDesc(true, 4L, pageable);
    }

    public Page<Orders> getStatus5(Integer currentPage, Integer size) {
        Pageable pageable = PageRequest.of(currentPage, size);
        return this.ordersRepository.findAllByDeletedAndStatusIdOrderByUpdatedDateDesc(true, 5L, pageable);
    }

    public OrderDTO getbyId(Long orderId) {
        Optional<Orders> orders = this.ordersRepository.findByIdAndDeleted(orderId, true);
        if (orders.isEmpty()) {
            throw new BadRequestException("Không tìm thấy đơn hàng này");
        }
        OrderDTO dto = MapperUtil.map(orders.get(), OrderDTO.class);
        if (dto.getVoucherId() == null) {
            dto.setVoucher(0d);
        }
        if (orders.get().getStatusId() != null) {
            Optional<OrderStatus> orderStatus = this.orderStatusRepository.findByIdAndDeleted(orders.get().getStatusId(), true);
            if (orderStatus.isPresent()) {
                dto.setStatus(orderStatus.get().getValueStatus());
            }
        }
        if (orders.get().getTypeOrder() == 1) {
            dto.setTypeOrders("Đặt online");
        } else if (orders.get().getTypeOrder() == 2) {
            dto.setTypeOrders("Đặt tại shop");
        }

        return dto;
    }

    public void changeStatus2(Long orderId) {
        Optional<Orders> orders = this.ordersRepository.findByIdAndDeleted(orderId, true);
        if (orders.isEmpty()) {
            throw new BadRequestException("Không tìm thấy đơn hàng này");
        }
        Orders orders1 = orders.get();
        orders1.setStatusId(2L);
        this.ordersRepository.save(orders1);
    }

    public void changeStatus3(Long orderId) {
        Optional<Orders> orders = this.ordersRepository.findByIdAndDeleted(orderId, true);
        if (orders.isEmpty()) {
            throw new BadRequestException("Không tìm thấy đơn hàng này");
        }
        Orders orders1 = orders.get();
        orders1.setStatusId(3L);
        this.ordersRepository.save(orders1);
    }

    public void changeStatus5(Long orderId) {
        Optional<Orders> orders = this.ordersRepository.findByIdAndDeleted(orderId, true);
        if (orders.isEmpty()) {
            throw new BadRequestException("Không tìm thấy đơn hàng này");
        }
        Orders orders1 = orders.get();
        orders1.setStatusId(5L);
        this.ordersRepository.save(orders1);
    }


    public void changeStatus4(Long orderId) {
        Optional<Orders> orders = this.ordersRepository.findByIdAndDeleted(orderId, true);
        if (orders.isEmpty()) {
            throw new BadRequestException("Không tìm thấy đơn hàng này");
        }
        Orders orders1 = orders.get();
        orders1.setStatusId(4L);
        this.ordersRepository.save(orders1);
    }

}
