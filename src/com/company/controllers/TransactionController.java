package com.company.controllers;

import com.company.controllers.interfaces.ITransactionController;
import com.company.exceptions.NotEnoughMoneyException;
import com.company.models.Account;
import com.company.models.Transaction;

import com.company.repositories.interfaces.IAccountRepository;
import com.company.repositories.interfaces.ITransactionRepository;
import com.company.utils.factories.TransactionFactory;
import java.util.List;

public class TransactionController implements ITransactionController {

    private final ITransactionRepository transactionRepo;
    private final IAccountRepository accountRepo;

    public TransactionController(ITransactionRepository transactionRepo, IAccountRepository accountRepo) {
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
    }

    @Override
    public void addIncome(int userId, int accountToId, int categoryId, double amount, String comment) {
        if (amount <= 0) {
            System.out.println("Amount must be > 0");
            return;
        }

        Account to = accountRepo.getById(accountToId);
        if (to == null) {
            System.out.println("Account not found: " + accountToId);
            return;
        }

        double newBalance = to.getBalance() + amount;
        if (!accountRepo.updateBalance(accountToId, newBalance)) {
            System.out.println("Failed to update balance");
            return;
        }

        // Было: new Transaction(...)
        // Стало:
        Transaction t = TransactionFactory.createIncome(
                userId, accountToId, categoryId, amount, comment
        );

        System.out.println(transactionRepo.create(t) ? "INCOME saved." : "INCOME not saved.");
    }

    @Override
    public void addExpense(int userId, int accountFromId, int categoryId, double amount, String comment) {
        if (amount <= 0) {
            System.out.println("Amount must be > 0");
            return;
        }

        Account from = accountRepo.getById(accountFromId);
        if (from == null) {
            System.out.println("Account not found: " + accountFromId);
            return;
        }

        if (from.getBalance() < amount) {
            throw new NotEnoughMoneyException();
        }

        double newBalance = from.getBalance() - amount;
        if (!accountRepo.updateBalance(accountFromId, newBalance)) {
            System.out.println("Failed to update balance");
            return;
        }

        Transaction t = TransactionFactory.createExpense(
                userId, accountFromId, categoryId, amount, comment
        );

        System.out.println(transactionRepo.create(t) ? "EXPENSE saved." : "EXPENSE not saved.");
    }

    @Override
    public void transfer(int userId, int fromAccountId, int toAccountId, double amount, String comment) {
        if (amount <= 0) {
            System.out.println("Amount must be > 0");
            return;
        }

        if (fromAccountId == toAccountId) {
            System.out.println("fromAccountId must be different from toAccountId");
            return;
        }

        Account from = accountRepo.getById(fromAccountId);
        Account to = accountRepo.getById(toAccountId);

        if (from == null || to == null) {
            System.out.println("Account not found");
            return;
        }

        if (from.getBalance() < amount) {
            throw new NotEnoughMoneyException();
        }

        double fromNew = from.getBalance() - amount;
        double toNew = to.getBalance() + amount;

        if (!accountRepo.updateBalance(fromAccountId, fromNew)) {
            System.out.println("Failed to update FROM balance");
            return;
        }
        if (!accountRepo.updateBalance(toAccountId, toNew)) {
            System.out.println("Failed to update TO balance");
            return;
        }

        Transaction t = TransactionFactory.createTransfer(
                userId, fromAccountId, toAccountId, amount, comment
        );

        System.out.println(transactionRepo.create(t) ? "TRANSFER saved." : "TRANSFER not saved.");
    }

    @Override
    public void showTransactions(int userId) {
        List<Transaction> list = transactionRepo.getByUserId(userId);

        if (list.isEmpty()) {
            System.out.println("No transactions.");
            return;
        }

        System.out.println("=== Transactions (sorted by id) ===");
        for (Transaction t : list) {
            String categoryId = t.getCategoryId() == null ? "-" : t.getCategoryId().toString();
            String fromId = t.getAccountFromId() == null ? "-" : t.getAccountFromId().toString();
            String toId = t.getAccountToId() == null ? "-" : t.getAccountToId().toString();
            String comment = t.getComment() == null || t.getComment().trim().isEmpty() ? "-" : t.getComment();
            System.out.printf(
                    "id=%d | type=%s | amount=%.2f | category=%s | from=%s | to=%s | at=%s | comment=%s%n",
                    t.getId(),
                    t.getType(),
                    t.getAmount(),
                    categoryId,
                    fromId,
                    toId,
                    t.getCreatedAt(),
                    comment
            );
        }
    }
}
