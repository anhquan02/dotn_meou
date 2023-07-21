package com.datn.meou.repository;

import com.datn.meou.entity.ExchangeItem;
import com.datn.meou.model.ExchangeItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ExchangeItem")
public interface ExchangeItemRepository extends JpaRepository<ExchangeItem, Long> {
    ExchangeItem findByIdAndDeleted(Long id, Boolean deleted);

    List<ExchangeItem> findAllByDeleted(Boolean delete);


    @Query(value = "select dei.*, de.name_customer,de.type_order,doi.name_product, doo.total_price FROM dotn_exchange_item dei \n" +
            "LEFT JOIN dotn_exchange de ON de.id = dei.exchange_id\n" +
            "LEFT JOIN dotn_order_item doi ON doi.id = dei.order_item_id\n" +
            "LEFT JOIN dotn_order doo ON doo.id = de.order_id WHERE dei.deleted = FALSE", nativeQuery = true)
    List<ExchangeItemDTO> findAllExchangeItemByName();

}
