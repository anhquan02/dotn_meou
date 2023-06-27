package com.datn.meou.services;

import com.datn.meou.entity.ExchangeItem;
import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.ExchangeItemDTO;
import com.datn.meou.repository.ExchangeItemRepository;
import com.datn.meou.repository.ExchangeRepository;
import com.datn.meou.util.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExchangeItemService {
    private final ExchangeItemRepository exchangeItemRepository;

    public void InsertOrUpdateExchangeItem(ExchangeItemDTO dto){

        ExchangeItem exItem = exchangeItemRepository.findByIdAndDeleted(dto.getId(), false);
        if(exItem == null || exItem.equals("")){
            exchangeItemRepository.save(MapperUtil.map(dto, ExchangeItem.class));
        }else{
            int quantity = exItem.getQuantity();
            exItem.setQuantity(quantity += dto.getQuantity());
            exchangeItemRepository.save(exItem);
        }
    }

    public void acceptExchangeItem(Long id){
        ExchangeItem  exItem = exchangeItemRepository.findByIdAndDeleted(id, false);
        if(exItem == null || exItem.equals("")){
            throw new BadRequestException("Không tìm thấy đơn hàng này");

        }else{
            exItem.setStatus(3);
        }
    }

    public void CancelExchangeItem(Long id){
        ExchangeItem  exItem = exchangeItemRepository.findByIdAndDeleted(id, false);
        if(exItem == null || exItem.equals("")){
            throw new BadRequestException("Không tìm thấy đơn hàng này");

        }else{
            exItem.setStatus(2);
        }
    }

//    public List<ExchangeItemDTO> getAllExchangeItemByCustomerId(Integer customerId){
//        List<ExchangeItem> lstExItem = exchangeItemRepository.findAllExchangeItemIdByCustomerIdAndDeleted(customerId, false);
//
//        if(lstExItem == null || lstExItem.isEmpty()){
//            throw new BadRequestException("Không tìm thấy đơn hàng này");
//        }
//
//        return MapperUtil.mapList(lstExItem, ExchangeItemDTO.class);
//    }

    public List<ExchangeItemDTO> getAllExchangeItem(){
        List<ExchangeItem> lstExchangeItems = exchangeItemRepository.findAll();

        if(lstExchangeItems == null || lstExchangeItems.isEmpty()){
            throw new BadRequestException("Không tìm thấy đơn hàng này");
        }
        return MapperUtil.mapList(lstExchangeItems, ExchangeItemDTO.class);
    }
}
