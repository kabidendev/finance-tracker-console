package com.company.controllers.interfaces;

import com.company.models.Account;
import java.util.List;

public interface IAccountController {
    Account createAccount(int userId, String name, double initialBalance);
    List<Account> listAccounts(int userId);
    Account showAccountDetails(int accountId);
}
