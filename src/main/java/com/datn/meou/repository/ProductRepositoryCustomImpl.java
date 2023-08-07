package com.datn.meou.repository;

import com.datn.meou.model.ProductDTO;
import com.datn.meou.util.CommonUtil;
import com.datn.meou.util.DataUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ProductDTO> searchProductForCounterSale(String nameProuduct) {
        StringBuilder sql = new StringBuilder(" SELECT pi.id as id,pi.quantity as quantity,p.price as price, pi.name as name, s.name as nameSole, ins.name as nameInsole,sz.name as nameSize,c.name as nameColor" +
                " FROM dotn_product_item pi   " +
                " JOIN dotn_product p ON p.id = pi.product_id" +
                " JOIN dotn_sole s ON s.id = pi.sole_id " +
                " JOIN dotn_insole ins ON ins.id = pi.insole_id" +
                " JOIN dotn_size sz ON sz.id = pi.size_id" +
                " JOIN dotn_color c ON c.id = pi.color_id" +
                " WHERE pi.status = 0  ");
        Map<String, Object> params = new HashMap<>();
        if (!DataUtil.isNullObject(nameProuduct)) {
            sql.append(" and lower(pi.name) like :name ");
            params.put("name", "%" + nameProuduct.toLowerCase() + "%");
        }
        return CommonUtil.getList(em, sql.toString(), params, "getProductByName");
    }
}
