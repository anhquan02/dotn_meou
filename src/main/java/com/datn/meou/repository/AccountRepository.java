package com.datn.meou.repository;

import com.datn.meou.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long > {
    @Query("select a from Account a where a.roleId=1")
    Page<Account> getListCustomer(Pageable pageable);

    @Query("select a from Account a where a.roleId=2 or a.roleId=3")
    Page<Account> getListStaff(Pageable pageable);

}
