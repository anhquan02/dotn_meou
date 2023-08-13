package com.datn.meou.repository;

import com.datn.meou.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long>, OrderRepositoryCustom {

    List<Orders> findAllByAccountId(Long accountId);

    List<Orders> findByCodeContainingOrderByIdDesc(String code);


}
