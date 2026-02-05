package com.company.controllers;

import com.company.controllers.interfaces.ITransactionController;
import com.company.exceptions.NotEnoughMoneyException;
import com.company.exceptions.NotFoundException;
import com.company.exceptions.ValidationException;
import com.company.models.*;
import com.company.repositories.interfaces.IAccountRepository;
import com.company.repositories.interfaces.ITransactionRepository;
import com.company.repositories.AccountRepository;


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
        try {
            Transaction tx = new IncomeTransaction(userId, amount, categoryId, accountToId, comment);

            tx.execute((AccountRepository) accountRepo);

            boolean saved = transactionRepo.create(tx);
            System.out.println(saved ? "INCOME saved." : "INCOME not saved.");

        } catch (NotEnoughMoneyException | NotFoundException | ValidationException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении дохода: " + e.getMessage());
        }
    }

    @Override
    public void addExpense(int userId, int accountFromId, int categoryId, double amount, String comment) {
        try {
            Transaction tx = new ExpenseTransaction(userId, amount, categoryId, accountFromId, comment);

            tx.execute((AccountRepository) accountRepo);

            boolean saved = transactionRepo.create(tx);
            System.out.println(saved ? "EXPENSE saved." : "EXPENSE not saved.");

        } catch (NotEnoughMoneyException | NotFoundException | ValidationException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении расхода: " + e.getMessage());
        }
    }

    @Override
    public void transfer(int userId, int fromAccountId, int toAccountId, double amount, String comment) {
        try {
            if (fromAccountId == toAccountId) {
                System.out.println("fromAccountId must be different from toAccountId");
                return;
            }

            Transaction tx = new TransferTransaction(userId, amount, fromAccountId, toAccountId, comment);

            tx.execute((AccountRepository) accountRepo);

            boolean saved = transactionRepo.create(tx);
            System.out.println(saved ? "TRANSFER saved." : "TRANSFER not saved.");

        } catch (NotEnoughMoneyException | NotFoundException | ValidationException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при переводе: " + e.getMessage());
        }
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