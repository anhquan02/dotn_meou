package com.datn.meou.services;

import com.datn.meou.entity.Exchange;
import com.datn.meou.model.ExchangeDTO;
import com.datn.meou.repository.ExchangeItemRepository;
import com.datn.meou.repository.ExchangeRepository;
import com.datn.meou.util.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExchangeService {

    private final ExchangeItemRepository exchangeItemRepository;

    private final ExchangeRepository exchangeRepository;

    public void createExchange(ExchangeDTO dto){
        exchangeRepository.save(MapperUtil.map(dto, Exchange.class));
    }

//    public void updateExchange(ExchangeDTO dto){
//        Optional<Exchange>  exchange =  exchangeRepository.findById(dto.getId());
//        if(exchange.isEmpty()){
//            throw new BadRequestException("Không tìm thấy đơn hàng này");
//        }
//        Exchange exchange1 = exchange.get();
//
//        exchange1.setOrderId(dto.getOrderId());
//
//        exchangeRepository.save(exchange1);
//    }
}
