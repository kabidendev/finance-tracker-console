package com.company.controllers.interfaces;

public interface ITransactionController {
    void addIncome(int userId, int accountToId, int categoryId, double amount, String comment);
    void addExpense(int userId, int accountFromId, int categoryId, double amount, String comment);
    void transfer(int userId, int fromAccountId, int toAccountId, double amount, String comment);
    void showTransactions(int userId);
}
