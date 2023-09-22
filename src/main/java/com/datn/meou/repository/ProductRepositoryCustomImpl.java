package com.datn.meou.repository;

import com.datn.meou.model.ProductDTO;
import com.datn.meou.model.StatisticalDTO;
import com.datn.meou.util.CommonUtil;
import com.datn.meou.util.DataUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ProductDTO> advancedSearch(ProductDTO dto) {
        StringBuilder sql = new StringBuilder(" SELECT p.id, p.name, p.image, p.brand_id AS brandId, " +
                " (SELECT MIN(dpi.price) FROM dotn_product_item dpi WHERE dpi.product_id = p.id) AS minPrice," +
                " (SELECT MAX(dpi.price) FROM dotn_product_item dpi WHERE dpi.product_id = p.id) AS maxPrice, " +
                " (SELECT SUM(dpi.quantity) FROM dotn_product_item dpi WHERE dpi.product_id = p.id) AS quantity, " +
                " p.status" +
                " FROM dotn_product p   " +
                " JOIN dotn_product_item dpi ON p.id = dpi.product_id " +
                " JOIN dotn_sole s ON s.id = dpi.sole_id  " +
                " JOIN dotn_insole ins ON ins.id = dpi.insole_id" +
                " JOIN dotn_size sz ON sz.id = dpi.size_id" +
                " JOIN dotn_color c ON c.id = dpi.color_id" +
                " JOIN dotn_brand b ON b.id = p.brand_id " +
                " WHERE p.status != 0");
        Map<String, Object> params = new HashMap<>();
        if (!DataUtil.isNullObject(dto.getName())) {
            sql.append(" and lower(p.name) like :name ");
            params.put("name", "%" + dto.getName().toLowerCase() + "%");
        }

        if (!DataUtil.isNullObject(dto.getBrandId())) {
            sql.append(" and p.brand_id = :brand");
            params.put("brand", dto.getBrandId());
        }
        if (!DataUtil.isNullObject(dto.getColorId())) {
            sql.append(" and dpi.color_id = :color");
            params.put("color", dto.getColorId());
        }
        if (!DataUtil.isNullObject(dto.getInsoleId())) {
            sql.append(" and dpi.insole_id = :insole");
            params.put("insole", dto.getInsoleId());
        }
        if (!DataUtil.isNullObject(dto.getSizeId())) {
            sql.append(" and dpi.size_id = :size");
            params.put("size", dto.getSizeId());
        }
        if (!DataUtil.isNullObject(dto.getSoleId())) {
            sql.append(" and dpi.sole_id = :sole");
            params.put("sole", dto.getSoleId());
        }
        if (!DataUtil.isNullObject(dto.getStatus())) {
            sql.append(" and p.status =  :status");
            params.put("status", dto.getStatus());
        }
        if (!DataUtil.isNullObject(dto.getId())) {
            sql.append(" and p.id = :id");
            params.put("id", dto.getId());
        }

        sql.append(" GROUP BY p.id");
        return CommonUtil.getList(em, sql.toString(), params, "advancedSearchProduct");
    }

    @Override
    public Page<ProductDTO> advancedSearchHomePage(ProductDTO dto, Pageable pageable) {
        StringBuilder sql = new StringBuilder(" SELECT p.id, p.name, p.image, p.brand_id AS brandId, " +
                " (SELECT MIN(dpi.price) FROM dotn_product_item dpi WHERE dpi.product_id = p.id) AS minPrice," +
                " (SELECT MAX(dpi.price) FROM dotn_product_item dpi WHERE dpi.product_id = p.id) AS maxPrice, " +
                " (SELECT SUM(dpi.quantity) FROM dotn_product_item dpi WHERE dpi.product_id = p.id) AS quantity, " +
                " p.status" +
                " FROM dotn_product p   " +
                " JOIN dotn_product_item dpi ON p.id = dpi.product_id " +
                " JOIN dotn_sole s ON s.id = dpi.sole_id  " +
                " JOIN dotn_insole ins ON ins.id = dpi.insole_id" +
                " JOIN dotn_size sz ON sz.id = dpi.size_id" +
                " JOIN dotn_color c ON c.id = dpi.color_id" +
                " JOIN dotn_brand b ON b.id = p.brand_id" +
                " WHERE p.status != 0");
        Map<String, Object> params = new HashMap<>();
        if (!DataUtil.isNullObject(dto.getName())) {
            sql.append(" and lower(p.name) like :name ");
            params.put("name", "%" + dto.getName().toLowerCase() + "%");
        }

        if (!DataUtil.isNullObject(dto.getBrandId())) {
            sql.append(" and p.brand_id = :brand");
            params.put("brand", dto.getBrandId());
        }
        if (!DataUtil.isNullObject(dto.getColorId())) {
            sql.append(" and dpi.color_id = :color");
            params.put("color", dto.getColorId());
        }
        if (!DataUtil.isNullObject(dto.getInsoleId())) {
            sql.append(" and dpi.insole_id = :insole");
            params.put("insole", dto.getInsoleId());
        }
        if (!DataUtil.isNullObject(dto.getSizeId())) {
            sql.append(" and dpi.size_id = :size");
            params.put("size", dto.getSizeId());
        }
        if (!DataUtil.isNullObject(dto.getSoleId())) {
            sql.append(" and dpi.sole_id = :sole");
            params.put("sole", dto.getSoleId());
        }
        if (!DataUtil.isNullObject(dto.getStatus())) {
            sql.append(" and p.status =  :status");
            params.put("status", dto.getStatus());
        }
        if (!DataUtil.isNullObject(dto.getId())) {
            sql.append(" and p.id = :id");
            params.put("id", dto.getId());
        }

        sql.append(" GROUP BY p.id ORDER BY p.id");
        return CommonUtil.getPageImpl(em, sql.toString(), params, pageable, "advancedSearchProduct");
    }

    @Override
    public Page<ProductDTO> advancedSearchPage(ProductDTO dto, Pageable pageable) {
        StringBuilder sql = new StringBuilder(" SELECT p.id, p.name, p.image, p.brand_id AS brandId," +
                " (SELECT MIN(dpi.price) FROM dotn_product_item dpi WHERE dpi.product_id = p.id) AS minPrice," +
                " (SELECT MAX(dpi.price) FROM dotn_product_item dpi WHERE dpi.product_id = p.id) AS maxPrice, " +
                " (SELECT SUM(dpi.quantity) FROM dotn_product_item dpi WHERE dpi.product_id = p.id) AS quantity, " +
                " p.status" +
                " FROM dotn_product p   " +
                " JOIN dotn_brand b ON b.id = p.brand_id, " +
                " dotn_product_item dpi" +
                " JOIN dotn_sole s ON s.id = dpi.sole_id  " +
                " JOIN dotn_insole ins ON ins.id = dpi.insole_id" +
                " JOIN dotn_size sz ON sz.id = dpi.size_id" +
                " JOIN dotn_color c ON c.id = dpi.color_id" +
                " WHERE p.status != 0");
        Map<String, Object> params = new HashMap<>();
        if (!DataUtil.isNullObject(dto.getName())) {
            sql.append(" and lower(p.name) like :name ");
            params.put("name", "%" + dto.getName().toLowerCase() + "%");
        }

        if (!DataUtil.isNullObject(dto.getBrandId())) {
            sql.append(" and p.brand_id = :brand");
            params.put("brand", dto.getBrandId());
        }
        if (!DataUtil.isNullObject(dto.getColorId())) {
            sql.append(" and dpi.color_id = :color");
            params.put("color", dto.getColorId());
        }
        if (!DataUtil.isNullObject(dto.getInsoleId())) {
            sql.append(" and dpi.insole_id = :insole");
            params.put("insole", dto.getInsoleId());
        }
        if (!DataUtil.isNullObject(dto.getSizeId())) {
            sql.append(" and dpi.size_id = :size");
            params.put("size", dto.getSizeId());
        }
        if (!DataUtil.isNullObject(dto.getSoleId())) {
            sql.append(" and dpi.sole_id = :sole");
            params.put("sole", dto.getSoleId());
        }
        if (!DataUtil.isNullObject(dto.getStatus())) {
            sql.append(" and p.status =  :status");
            params.put("status", dto.getStatus());
        }
        if (!DataUtil.isNullObject(dto.getId())) {
            sql.append(" and p.id = :id");
            params.put("id", dto.getId());
        }

        sql.append(" GROUP BY p.id ORDER BY p.id");
        return CommonUtil.getPageImpl(em, sql.toString(), params, pageable, "advancedSearchProduct");
    }

    @Override
    public ProductDTO getByIdForOnline(Long id) {
        StringBuilder sql = new StringBuilder(" SELECT p.id, p.name, p.image, " +
                " (SELECT MIN(dpi.price) FROM dotn_product_item dpi WHERE dpi.product_id = p.id) AS minPrice," +
                " (SELECT MAX(dpi.price) FROM dotn_product_item dpi WHERE dpi.product_id = p.id) AS maxPrice, " +
                " p.status,p.description ,p.brand_id as brandId ,b.name as nameBrand " +
                " FROM dotn_product p ,dotn_brand b  " +
                " WHERE p.status = 1 " +
                " AND p.brand_id = b.id ");
        Map<String, Object> params = new HashMap<>();
        sql.append(" AND p.id = :id");
        params.put("id", id);
        return CommonUtil.getObject(em, sql.toString(), params, "payOnline");
    }

    @Override
    public StatisticalDTO getForMonthly() {
        StringBuilder sql = new StringBuilder("SELECT COUNT(do.id) AS monthQuantity, SUM(do.total_price) AS monthPrice, SUM(doi.quantity_order) AS quantityOrder FROM dotn_order do " +
                "JOIN dotn_order_item doi ON doi.order_id = do.id" +
                " WHERE MONTH(do.updated_date) = MONTH(CURDATE()) AND do.status_id = 5");
        Map<String, Object> params = new HashMap<>();
        return CommonUtil.getObject(em, sql.toString(), params, "statisticalMonthly");
    }

    @Override
    public StatisticalDTO getForToday() {
        StringBuilder sql = new StringBuilder("SELECT COUNT(do.id) AS todayQuantity, SUM(do.total_price) AS todayPrice FROM dotn_order do " +
                " WHERE DATE(do.updated_date) = DATE(CURDATE()) AND do.status_id = 5;");
        Map<String, Object> params = new HashMap<>();
        return CommonUtil.getObject(em, sql.toString(), params, "statisticalToday");
    }
}
