package com.company.repositories.interfaces;

import com.company.models.Account;

import java.util.List;

public interface IAccountRepository extends IRepository<Account> {
    List<Account> getByUserId(int userId);
    boolean updateBalance(int accountId, double newBalance);
}
