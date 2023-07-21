package com.datn.meou.services;

import com.datn.meou.entity.Exchange;
import com.datn.meou.entity.ExchangeItem;
import com.datn.meou.entity.OrderItem;
import com.datn.meou.entity.Orders;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.ExchangeDTO;
import com.datn.meou.model.ExchangeItemDTO;
import com.datn.meou.repository.*;
import com.datn.meou.util.MapperUtil;
import javassist.expr.NewArray;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExchangeItemService {
    private final ExchangeItemRepository exchangeItemRepository;

    private final ExchangeRepository exchangeRepository;

    private final OrdersRepository ordersRepository;

    private final OrderItemRepository orderItemRepository;



//    public List<ExchangeItemDTO> getAllExchangeItem1(){
//
//        List<ExchangeItem> lstExchangeItems = exchangeItemRepository.findAll();
//
//
//
//        if(lstExchangeItems == null || lstExchangeItems.isEmpty()){
//            throw new BadRequestException("Không tìm thấy đơn hàng này");
//        }
//        return MapperUtil.mapList(lstExchangeItems, ExchangeItemDTO.class);
//    }

    public List<ExchangeItemDTO> getAllExchangeItem(){

        List<ExchangeItemDTO> lstExchangeItems = exchangeItemRepository.findAllExchangeItemByName();



        if(lstExchangeItems == null || lstExchangeItems.isEmpty()){
            throw new BadRequestException("Không tìm thấy đơn hàng này");
        }
        return lstExchangeItems;
    }

    public List<ExchangeItemDTO> getAllExchangeItem1(){
        List<ExchangeItemDTO> lstDTO = new ArrayList<>();

        List<ExchangeItem> lstItem = exchangeItemRepository.findAllByDeleted(false);

        for (ExchangeItem item : lstItem) {
            ExchangeItemDTO dto = new ExchangeItemDTO();
            dto.setId(item.getId());
            dto.setOrderItemId(item.getOrderItemId());
            dto.setQuantity(item.getQuantity());
            dto.setStatus(item.getStatus());

            Optional<Exchange> exchange = exchangeRepository.findById(item.getExchangeId());

            dto.setTypeOrder(exchange.get().getTypeOrder());
            if(exchange.get().getTypeOrder() == 1){
                dto.setTypeOrderName("Online");
            }else{
                dto.setTypeOrderName("Tại Quầy");
            }
            dto.setCustomerName(exchange.get().getNameCustomer());

            Optional<OrderItem> orderItem = orderItemRepository.findById(item.getOrderItemId());

            dto.setProductName(orderItem.get().getNameProduct());

            Optional<Orders> orders = ordersRepository.findById(orderItem.get().getOrderId());

            dto.setCreateDateOrder(orders.get().getCreatedDate());

            lstDTO.add(dto);

        }
        return lstDTO;

    }

    public List<ExchangeItemDTO> getEchangeItemStatus(){
        List<ExchangeItemDTO> lstDTO = new ArrayList<>();

        List<ExchangeItem> lstItem = exchangeItemRepository.findAllByDeleted(true);

        for (ExchangeItem item : lstItem) {
            ExchangeItemDTO dto = new ExchangeItemDTO();
            dto.setId(item.getId());
            dto.setOrderItemId(item.getOrderItemId());
            dto.setQuantity(item.getQuantity());
            dto.setStatus(item.getStatus());

            if(dto.getStatus() == 3){
                dto.setStatusName("Chấp nhận");
            } else if (dto.getStatus() == 2) {
                dto.setStatusName("Từ chối");
            }else{
                dto.setStatusName("Đang xử lý");
            }

            Optional<Exchange> exchange = exchangeRepository.findById(item.getExchangeId());

            dto.setTypeOrder(exchange.get().getTypeOrder());
            if(exchange.get().getTypeOrder() == 1){
                dto.setTypeOrderName("Online");
            }else{
                dto.setTypeOrderName("Tại Quầy");
            }
            dto.setCustomerName(exchange.get().getNameCustomer());

            Optional<OrderItem> orderItem = orderItemRepository.findById(item.getOrderItemId());

            dto.setProductName(orderItem.get().getNameProduct());

            Optional<Orders> orders = ordersRepository.findById(orderItem.get().getOrderId());

            dto.setCreateDateOrder(orders.get().getCreatedDate());

            lstDTO.add(dto);

        }
        return lstDTO;

    }

    public void InsertOrUpdateExchangeItem(ExchangeItemDTO dto){
        Date date = new Date();
        Optional<Exchange> exchange = exchangeRepository.findById(dto.getOrderId());
        if(exchange == null){
            Exchange exchange1 = new Exchange();
            exchange1.setCreatedDate(date);
            exchange1.setUpdatedDate(date);
            exchange1.setOrderId(dto.getOrderId());
            exchange1.setTypeOrder(dto.getTypeOrder());
            exchange1.setAccountId(dto.getAccountId());
            exchange1.setNameCustomer(dto.getCustomerName());
            exchange1.setTypeOrder(dto.getTypeOrder());
            exchangeRepository.save(exchange1);

            exchangeItemRepository.save(MapperUtil.map(dto, ExchangeItem.class));
        }else{
            exchangeItemRepository.save(MapperUtil.map(dto, ExchangeItem.class));
        }
    }

    public void acceptExchangeItem(Long id){
        ExchangeItem  exItem = exchangeItemRepository.findByIdAndDeleted(id, false);
        if(exItem == null || exItem.equals("")){
            throw new BadRequestException("Không tìm thấy đơn hàng này");

        }else{
            exItem.setStatus(3);
            exItem.setDeleted(true);
            exchangeItemRepository.save(exItem);
        }
    }

    public void CancelExchangeItem(Long id){
        ExchangeItem  exItem = exchangeItemRepository.findByIdAndDeleted(id, false);
        if(exItem == null || exItem.equals("")){
            throw new BadRequestException("Không tìm thấy đơn hàng này");

        }else{
            exItem.setStatus(2);
            exItem.setDeleted(true);
            exchangeItemRepository.save(exItem);
        }
    }



}
