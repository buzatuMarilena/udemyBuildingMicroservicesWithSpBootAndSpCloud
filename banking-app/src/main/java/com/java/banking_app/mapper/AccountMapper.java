package com.java.banking_app.mapper;


import com.java.banking_app.dto.AccountDto;
import com.java.banking_app.entity.Account;

public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto){
        return new Account(
                accountDto.id(),
                accountDto.accountHolderName(),
                accountDto.balance()
        );
    }

    public static AccountDto mapToAccountDto(Account account){
        return new AccountDto(
          account.getId(),
          account.getAccountHolderName(),
          account.getBalance()
        );
    }
}
