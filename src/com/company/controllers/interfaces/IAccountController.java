package com.company.controllers.interfaces;

public interface IAccountController {
    void createAccount(int userId, String name, double initialBalance);
    void showAccounts(int userId);
    void showAccountDetails(int accountId);
}
