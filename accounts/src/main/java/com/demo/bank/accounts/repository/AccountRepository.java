package com.demo.bank.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.bank.accounts.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
}
