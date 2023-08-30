package com.datn.meou.repository;

import com.datn.meou.exception.BadRequestException;
import com.datn.meou.model.ProductDTO;
import com.datn.meou.model.ProductItemDTO;
import com.datn.meou.model.StatisticalDTO;
import com.datn.meou.model.StatisticalDTOS;
import com.datn.meou.util.CommonUtil;
import com.datn.meou.util.DataUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
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
                "sz.name as nameSize,c.name as nameColor,b.name as nameBrand,pi.sole_id as soleId," +
                " pi.sole_id as soleId ,pi.insole_id as insoleId, pi.size_id as sizeId , pi.color_id as colorId, p.brand_id as brandId ,pi.status as status " +
                " FROM dotn_product_item pi   " +
                " JOIN dotn_product p ON p.id = pi.product_id" +
                " JOIN dotn_sole s ON s.id = pi.sole_id " +
                " JOIN dotn_insole ins ON ins.id = pi.insole_id" +
                " JOIN dotn_size sz ON sz.id = pi.size_id" +
                " JOIN dotn_color c ON c.id = pi.color_id" +
                " JOIN dotn_brand b ON b.id = p.brand_id" +
                " WHERE pi.status = 1 and pi.quantity >0   ");
        Map<String, Object> params = new HashMap<>();
        if (!DataUtil.isNullObject(dto.getName())) {
            sql.append(" and lower(pi.name) like :name ");
            params.put("name", "%" + dto.getName().toLowerCase() + "%");
        }

        if (!DataUtil.isNullObject(dto.getBrandId())) {
            sql.append(" and p.brand_id = :brand");
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

    @Override
    public List<StatisticalDTOS> topSales() {
        StringBuilder sql = new StringBuilder("SELECT b.name AS name, b.price AS priceProductItem, SUM(a.quantity_order) AS totalQuantity FROM dotn_order_item a\n" +
                "INNER JOIN dotn_product_item b ON a.product_item_id = b.id\n" +
                "WHERE a.product_item_id  \n" +
                "IN (SELECT doi.product_item_id FROM dotn_order_item doi \n" +
                "JOIN dotn_order do ON doi.order_id = do.id\n" +
                "WHERE MONTH(do.updated_date) = MONTH(CURDATE()) AND do.status_id = 5\n" +
                "GROUP BY doi.product_item_id)\n" +
                "GROUP BY a.product_item_id, b.name, b.price, a.quantity_order\n" +
                " ORDER BY totalQuantity DESC" +
                " LIMIT 5");
        Map<String, Object> params = new HashMap<>();
        return CommonUtil.getList(em, sql.toString(), params, "topSales");
    }

    @Override
    public List<StatisticalDTOS> getStatisticalByDate(Date fromDate, Date toDate) {
        StringBuilder sql = new StringBuilder("SELECT date(do.updated_date) AS saleDate, SUM(doi.quantity_order) AS todayQuantity, SUM(doi.price_sell) AS totalPrice " +
                "FROM dotn_order_item doi\n" +
                "JOIN dotn_order do ON doi.order_id = do.id\n" +
                "WHERE do.status_id = 5 ");
        Map<String, Object> params = new HashMap<>();
        if(!DataUtil.isNullObject(fromDate) && !DataUtil.isNullObject(toDate)){
            if(toDate.before(fromDate)){
                throw new BadRequestException("Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
            }
            sql.append(" AND date(do.updated_date) BETWEEN :fromDate ");
            params.put("fromDate", fromDate);
            sql.append(" AND :toDate");
            params.put("toDate", toDate);

            sql.append(" GROUP BY saleDate ORDER BY saleDate");
        }else{
            sql.append(" AND MONTH(do.updated_date) = MONTH(CURDATE()) GROUP BY saleDate  ORDER BY saleDate LIMIT 30");
        }
        return CommonUtil.getList(em, sql.toString(), params, "getStatisticalByDate");
    }
}
