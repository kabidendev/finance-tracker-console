package com.company.controllers;

import com.company.controllers.interfaces.IAccountController;
import com.company.exceptions.ValidationException;
import com.company.models.Account;
import com.company.repositories.interfaces.IAccountRepository;

import java.util.List;

public class AccountController implements IAccountController {

    private final IAccountRepository accountRepository;

    public AccountController(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(int userId, String name, double initialBalance) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Account name cannot be empty");
        }
        if (initialBalance < 0) {
            throw new ValidationException("Initial balance cannot be negative");
        }

        Account account = new Account(userId, name.trim(), initialBalance);
        accountRepository.create(account);
        return account;
    }

    @Override
    public List<Account> listAccounts(int userId) {
        return accountRepository.getByUserId(userId);
    }

    @Override
    public Account showAccountDetails(int accountId) {
        return accountRepository.getById(accountId);
    }
}