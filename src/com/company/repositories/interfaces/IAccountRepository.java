package com.company.repositories.interfaces;

import com.company.models.Account;

public interface IAccountRepository extends IRepository<Account> {
    boolean updateBalance(int accountId, double newBalance);
}
