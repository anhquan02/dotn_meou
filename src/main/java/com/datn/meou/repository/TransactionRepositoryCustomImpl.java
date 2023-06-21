package com.datn.meou.repository;


import com.datn.meou.model.TransactionDTO;
import com.datn.meou.util.CommonUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepositoryCustomImpl implements TransactionRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<TransactionDTO> findByNameOrders(String name, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT tr.id,ord.code as codeOrder,tr.created_date as creatDate,ord.name_customer as nameCustomer,ord.phone_customer as phoneCustomer,ord.type_order as type,tr.total_price as totalPrice" +
                "  FROM dotn_transaction tr JOIN dotn_order ord ON tr.order_id = ord.id " +
                " WHERE ord.deleted = true  ");

        Map<String, Object> params = new HashMap<>();
        sql.append(" and lower(ord.code) LIKE :name ");
        params.put("name", "%" + name.toLowerCase() + "%");
        return CommonUtil.getPageImpl(em, sql.toString(), params, pageable, "getTransactionByCode");
    }
}
