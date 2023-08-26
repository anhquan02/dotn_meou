package com.datn.meou.repository;

import com.datn.meou.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsernameAndStatus(String username, Boolean status);

    Optional<Account> findByIdAndStatus(Long id, Boolean status);
    List<Account> findByRoleIdAndStatusAndPhoneContaining(Long roleId, Boolean status, String phone);

    List<Account> findByRoleIdAndStatus(Long roleId, Boolean status);

    Optional<Account> findByUsername(String username);

}
