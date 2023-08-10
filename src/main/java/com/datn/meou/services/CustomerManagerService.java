package com.datn.meou.services;

import com.datn.meou.entity.Orders;
import com.datn.meou.model.OrderDTO;
import com.datn.meou.model.OrderItem1DTO;
import com.datn.meou.repository.OrdersRepository;
import com.datn.meou.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerManagerService {

    @Autowired
    OrdersRepository ordersRepository;

    public List<OrderDTO> getOrderById(Long idCustomer){

        List<Orders> lstOrder = ordersRepository.findAllByAccountId(idCustomer);

        return MapperUtil.mapList(lstOrder, OrderDTO.class);
    }
}
