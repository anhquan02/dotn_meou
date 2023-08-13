package com.datn.meou.repository;


import com.datn.meou.model.OrderDTO;
import com.datn.meou.util.CommonUtil;
import com.datn.meou.util.DataUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<OrderDTO> findAll(OrderDTO dto, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o.id AS id , o.code AS code ,o.created_date AS createdDate,o.address_customer AS addressCustomer, ")
                .append("o.email_customer AS emailCustomer, o.name_customer AS nameCustomer, o.note as note , ")
                .append(" o.total_price AS totalPrice ,o.type_order AS typeOrder,o.phone_customer AS phoneCustomer , ")
                .append(" o.payment_method AS paymentMethod ,os.value_status AS valueStatus,a.username AS username,o.status_id AS statusId ")
                .append(" FROM dotn_order o, dotn_order_status os ,dotn_account a ")
                .append(" WHERE o.status_id = os.id AND a.id = o.account_id ")
                .append(" AND os.status = true ");
        Map<String, Object> params = new HashMap<>();
        if (!DataUtil.isNullObject(dto.getCode())) {
            sql.append(" and lower(o.code) like :code ");
            params.put("code", "%" + dto.getCode().toLowerCase() + "%");
        }
        if (!DataUtil.isNullObject(dto.getNameCustomer())) {
            sql.append(" and lower(o.name_customer) like :nameCustomer ");
            params.put("nameCustomer", "%" + dto.getNameCustomer().toLowerCase() + "%");
        }

        if (!DataUtil.isNullObject(dto.getPhoneCustomer())) {
            sql.append(" and lower(o.phone_customer) like :phoneCustomer ");
            params.put("phoneCustomer", "%" + dto.getPhoneCustomer().toLowerCase() + "%");
        }

        sql.append(" ORDER BY o.created_date DESC");


        return CommonUtil.getPageImpl(em, sql.toString(), params, pageable, "getAllOrder");
    }

    @Override
    public OrderDTO findByOrderId(Long idOrder) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o.id AS id , o.code AS code ,o.created_date AS createdDate,o.address_customer AS addressCustomer, ")
                .append("o.email_customer AS emailCustomer, o.name_customer AS nameCustomer, o.note as note , ")
                .append(" o.total_price AS totalPrice ,o.type_order AS typeOrder,o.phone_customer AS phoneCustomer , ")
                .append(" o.payment_method AS paymentMethod ,os.value_status AS valueStatus,a.username AS username,o.status_id AS statusId ")
                .append(" FROM dotn_order o, dotn_order_status os ,dotn_account a ")
                .append(" WHERE o.status_id = os.id AND a.id = o.account_id ")
                .append(" AND os.status = true ");
        Map<String, Object> params = new HashMap<>();
        sql.append(" AND o.id = :idOrder");
        params.put("idOrder", idOrder);

        return CommonUtil.getObject(em, sql.toString(), params, "getAllOrder");
    }
}
