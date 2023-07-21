package com.datn.meou.repository;

import com.datn.meou.model.ExchangeItemDTO;
import com.datn.meou.util.CommonUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class ExchangeItemRepositoryImpl implements ExchangeItemRepositoryCustom  {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ExchangeItemDTO> findAllExchangeItem(String name) {
        StringBuilder sql = new StringBuilder("SELECT dei.*, de.name_customer,de.type_order,doi.name_product  " +
                "  FROM dotn_exchange_item dei LEFT JOIN dotn_exchange de ON de.id = dei.exchange_id LEFT JOIN dotn_order_item doi ON doi.id = dei.order_item_id" +
                " WHERE dei.deleted = FALSE  ");

        Map<String, Object> params = new HashMap<>();
        sql.append(" and lower(de.name_customer) LIKE :name ");
        params.put("name", "%" + name.toLowerCase() + "%");
        return CommonUtil.getList(em, sql.toString(), params,"getExchangeItemByCustomerName");
    }



}
