package com.java.banking_app.service;

import com.java.banking_app.exception.ResourceNotFoundException;
import com.java.banking_app.dto.AccountDto;
import com.java.banking_app.entity.Account;
import com.java.banking_app.mapper.AccountMapper;
import com.java.banking_app.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private static final String ACCOUNT_NOT_FOUND_MESSAGE = "Account does not exist with id: ";

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = findAccountById(id);
        return AccountMapper.mapToAccountDto(account);
    }

    @Transactional
    @Override
    public AccountDto deposit(Long id, double amount) {
        logger.info("Depositing {} to account ID: {}", amount, id);
        Account account = findAccountById(id);

        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }

        account.setBalance(account.getBalance() + amount);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Transactional
    @Override
    public AccountDto withdraw(Long id, double amount) {
        logger.info("Withdrawing {} from account ID: {}", amount, id);
        Account account = findAccountById(id);

        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0");
        }

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(AccountMapper::mapToAccountDto)
                .toList();
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = findAccountById(id);
        accountRepository.delete(account);
    }

    private Account findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND_MESSAGE + id));
    }
}
