package com.datn.meou.repository;

import com.datn.meou.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Page<Orders> findAllByDeletedOrderByCreatedDateDesc(Boolean deleted, Pageable pageable);

    Optional<Orders> findByIdAndDeleted(Long id, Boolean deleted);

    List<Orders> findAllByDeletedAndStatusId(Boolean deleted, Long statusId);

    Page<Orders> findAllByDeletedAndStatusIdOrderByUpdatedDateDesc(Boolean deleted, Long statusId, Pageable pageable);

    Page<Orders> findAllByDeletedAndStatusIdOrderByCreatedDateDesc(Boolean deleted, Long statusId, Pageable pageable);

    Page<Orders> findAllByDeletedAndCodeContaining(Boolean deleted, String code, Pageable pageable);

}
