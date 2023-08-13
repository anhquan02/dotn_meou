package com.datn.meou.repository;

import com.datn.meou.model.ProductDTO;
import com.datn.meou.model.ProductItemDTO;
import com.datn.meou.util.CommonUtil;
import com.datn.meou.util.DataUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductItemRepositoryCustomImpl implements ProductItemRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ProductItemDTO> searchProductForCounterSale(ProductItemDTO dto) {
        StringBuilder sql = new StringBuilder(" SELECT pi.id as id,pi.quantity as quantity,pi.price as price," +
                " pi.name as name, s.name as nameSole, ins.name as nameInsole," +
                "sz.name as nameSize,c.name as nameColor" +
                " FROM dotn_product_item pi   " +
                " JOIN dotn_product p ON p.id = pi.product_id" +
                " JOIN dotn_sole s ON s.id = pi.sole_id " +
                " JOIN dotn_insole ins ON ins.id = pi.insole_id" +
                " JOIN dotn_size sz ON sz.id = pi.size_id" +
                " JOIN dotn_color c ON c.id = pi.color_id" +
                " JOIN dotn_brand b ON b.id = p.brand_id" +
                " WHERE pi.status = 1  ");
        Map<String, Object> params = new HashMap<>();
        if (!DataUtil.isNullObject(dto.getName())) {
            sql.append(" and lower(pi.name) like :name ");
            params.put("name", "%" + dto.getName().toLowerCase() + "%");
        }

        if (!DataUtil.isNullObject(dto.getBrandId())) {
            sql.append(" and pi.brand_id = :brand");
            params.put("brand", dto.getBrandId());
        }
        if (!DataUtil.isNullObject(dto.getColorId())) {
            sql.append(" and pi.color_id = :color");
            params.put("color", dto.getColorId());
        }
        if (!DataUtil.isNullObject(dto.getInsoleId())) {
            sql.append(" and pi.insole_id = :insole");
            params.put("insole", dto.getInsoleId());
        }
        if (!DataUtil.isNullObject(dto.getSizeId())) {
            sql.append(" and pi.size_id = :size");
            params.put("size", dto.getSizeId());
        }
        if (!DataUtil.isNullObject(dto.getSoleId())) {
            sql.append(" and pi.sole_id = :sole");
            params.put("sole", dto.getSoleId());
        }

        return CommonUtil.getList(em, sql.toString(), params, "getProductByName");
    }
}
