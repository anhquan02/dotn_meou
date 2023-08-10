package com.datn.meou.repository;


import com.datn.meou.model.OrderItemDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderItemRepositoryCustomImpl implements OrderItemRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


}
