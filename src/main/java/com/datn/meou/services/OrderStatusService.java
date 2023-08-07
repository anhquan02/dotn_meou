package com.datn.meou.services;


import com.datn.meou.entity.OrderStatus;
import com.datn.meou.entity.Orders;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.OrderStatusDTO;
import com.datn.meou.repository.OrderStatusRepository;
import com.datn.meou.repository.OrdersRepository;
import com.datn.meou.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
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
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    private final OrdersRepository ordersRepository;

    public OrderStatus save(OrderStatusDTO dto) {
        OrderStatus orderStatus = OrderStatus
                .builder()
                .valueStatus(dto.getValueStatus())
                .build();
//        orderStatus.setDeleted(true);
        this.orderStatusRepository.save(orderStatus);
        return orderStatus;
    }

    public OrderStatus update(OrderStatusDTO dto) {
        Optional<OrderStatus> orderStatus = this.orderStatusRepository.findByIdAndDeleted(dto.getId(), true);
        if (orderStatus.isEmpty()) {
            throw new BadRequestException("Không tìm thấy id");
        }
        OrderStatus status = MapperUtil.map(dto, OrderStatus.class);
//        status.setDeleted(true);
        return this.orderStatusRepository.save(status);
    }

    public void delete(Long idStatus) {
        Optional<OrderStatus> orderStatus = this.orderStatusRepository.findByIdAndDeleted(idStatus, true);
        if (orderStatus.isEmpty()) {
            throw new BadRequestException("Không tìm thấy id");
        }
        List<Orders> ordersList = this.ordersRepository.findAllByDeletedAndStatusId(true, idStatus);
        if (ordersList.size() > 0) {
            throw new BadRequestException("Đã có trạng thái cho đơn hàng ");
        }
        OrderStatus status = orderStatus.get();
//        status.setDeleted(false);
        this.orderStatusRepository.save(status);
    }

    public Page<OrderStatusDTO> findAll(Integer currentPage, Integer size) {
        Pageable pageable = PageRequest.of(currentPage, size);
        Page<OrderStatus> orderStatuses = this.orderStatusRepository.findAllByDeleted(true, pageable);
        return MapperUtil.mapEntityPageIntoDtoPage(orderStatuses, OrderStatusDTO.class);
    }

    public OrderStatusDTO getbyId(Long id) {
        Optional<OrderStatus> orderStatus = this.orderStatusRepository.findByIdAndDeleted(id, true);
        if (orderStatus.isEmpty()) {
            throw new BadRequestException("Không tìm thấy id");
        }
        return MapperUtil.map(orderStatus.get(), OrderStatusDTO.class);
    }


}
