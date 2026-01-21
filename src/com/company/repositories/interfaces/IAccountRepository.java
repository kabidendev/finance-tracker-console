package com.company.repositories.interfaces;

import com.company.models.Account;

import java.util.List;

public interface IAccountRepository extends IRepository<Account> {
    boolean updateBalance(int accountId, double newBalance);
}
