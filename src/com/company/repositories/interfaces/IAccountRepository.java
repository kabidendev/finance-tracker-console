package com.company.repositories.interfaces;

import com.company.models.Account;

import java.util.List;

public interface IAccountRepository extends IRepository<Account> {
        void create(Account account);
        Account getById(int id);
        List<Account> getByUserId(int userId);
        void updateBalance(int accountId, double newBalance);
}
