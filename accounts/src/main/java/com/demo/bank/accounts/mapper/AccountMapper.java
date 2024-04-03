package com.demo.bank.accounts.mapper;

import com.demo.bank.accounts.dto.AccountDto;
import com.demo.bank.accounts.entity.Account;

/**
 * AccountMapper maps DTO and Entity for Account
 */
public class AccountMapper {
    public static AccountDto mapToAccountDto(Account account, AccountDto accountDto) {
        accountDto.setAccountNumber(account.getAccountNumber().toString());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
        return accountDto;
    }

    public static Account mapToAccount(AccountDto accountDto, Account account) {
        account.setAccountNumber(Long.parseLong(accountDto.getAccountNumber()));
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());
        return account;
    }
}
