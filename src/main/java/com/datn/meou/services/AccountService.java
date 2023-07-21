package com.datn.meou.services;

import com.datn.meou.entity.Account;
import com.datn.meou.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Page<Account> getListCustomer(Integer page, Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return accountRepository.getListCustomer(pageable);
    }
    public Page<Account> getListStaff(Integer page, Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return accountRepository.getListStaff(pageable);
    }

    public Account saveAccount(Account account){
        return this.accountRepository.save(account);
    }

    public Account findById(Long id){
        return accountRepository.findById(id).orElse(null);
    }

    public void deleteAccount(Long id){
        Account account = this.findById(id);
        if (account !=null){
            account.setDeleted(!account.getDeleted());
            this.accountRepository.save(account);
        }
    }
}
